package com.tongjisse.adventure.view.views.Story

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.j256.ormlite.stmt.query.In
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.StoryList
import com.tongjisse.adventure.presenter.Story.StoryListPresenter
import com.tongjisse.adventure.utils.CityPickerHelper
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.BaseFragmentWithPresenter
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.MainListAdapter
import com.tongjisse.adventure.view.main.Story.StoryListAdapter
import com.tongjisse.adventure.view.main.Story.StoryListView
import kotlinx.android.synthetic.main.fragment_story.*
import kotlinx.android.synthetic.main.story_adapter_item.*
import java.sql.SQLException

class StoryListFragment : BaseFragmentWithPresenter(), StoryListView {

    private val SHOW_BY_PLACE = 1
    private val SHOW_BY_TITLE = 2
    private val SHOW_BY_SCENE = 3
    private var showType = SHOW_BY_PLACE
    lateinit var mSessionManager: SessionManager
    override val presenter by lazy { StoryListPresenter(this) }
    lateinit var mCityPickerHelper: CityPickerHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_story, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        storyRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mSessionManager = SessionManager(context)
        mCityPickerHelper = CityPickerHelper(context, object : OnCityItemClickListener() {
            override fun onSelected(province: ProvinceBean?, city: CityBean?, district: DistrictBean?) {
                mSessionManager.refineLocation(province!!.name, city!!.name, district!!.name, mSessionManager.longitude, mSessionManager.latitude)
                tvLocation.text = mSessionManager.defaultAddress
            }

            override fun onCancel() {

            }
        })

        swipeRefreshView.setOnRefreshListener {
            swipeRefreshView.isRefreshing = true
            loadStoryLists()
            swipeRefreshView.isRefreshing = false
        }

        presenter.loadStoriesByDistrict(mSessionManager.defaultAddress)

        tvLocation.setOnClickListener {
            mCityPickerHelper.showJD()
        }

        bPublish.setOnClickListener() {
            val intent = Intent(context, StoryPublishActivity::class.java)
            intent.putExtra("email", mSessionManager.email)
            startActivity(intent)
        }

        etSearchStory.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //这里应该细化，根据Spinner选择
                if (mSessionManager.district.equals("")) {
                    mCityPickerHelper.showJD()
                } else if (etSearchStory.text.toString().split(" ").get(0) == "") {
                    showType = SHOW_BY_PLACE
                    presenter.loadStoriesByDistrict(mSessionManager.defaultAddress)
                } else {
                    when (spType.selectedItem.toString()) {
                        "标题" -> {
                            showType = SHOW_BY_TITLE
                            presenter.loadStoriesByTitle(etSearchStory.text.toString(), mSessionManager.defaultAddress)
                        }
                        "景点" -> {
                            showType = SHOW_BY_SCENE
                            presenter.loadStoriesByScene(etSearchStory.text.toString(), mSessionManager.defaultAddress)
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Not need to override
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Not need to override
            }
        })
    }

    override fun getStoryListsSuccess(userStoryLists: List<StoryList>) {
        if (userStoryLists != null && userStoryLists.isNotEmpty()) {
            ErrorLayout.visibility = View.GONE
            storyRecyclerView.visibility = View.VISIBLE
            val categoryItemAdapters = userStoryLists.map(::StoryListAdapter)
            storyRecyclerView.adapter = MainListAdapter(categoryItemAdapters)
        } else {
            ErrorLayout.visibility = View.VISIBLE
            storyRecyclerView.visibility = View.GONE
            tvError.text = "你还没有游记哦......快记录旅行途中有趣的故事吧！"
        }
    }

    override fun getStoryListsFailed(error: SQLException?) {
        if (error != null) {
            ErrorLayout.visibility = View.VISIBLE
            storyRecyclerView.visibility = View.GONE
            tvError.text = "加载游记失败......"
            context!!.toast("加载游记失败，错误信息:${error.message}")
        } else {
            ErrorLayout.visibility = View.VISIBLE
            storyRecyclerView.visibility = View.GONE
            tvError.text = "在${mSessionManager.district}还没有游记哦～"
        }
    }


    /**
     * 保持在返回该 Fragment 时数据最新
     *
     * @author Feifan Wang
     */
    override fun onResume() {
        super.onResume()
        loadStoryLists()
    }

    /**
     * 根据搜索条件加载界面
     *
     * @author Feifan Wang
     */
    fun loadStoryLists() {
        when (showType) {
            SHOW_BY_PLACE -> {
                presenter.loadStoriesByDistrict(mSessionManager.defaultAddress)
            }
            SHOW_BY_TITLE -> {
                presenter.loadStoriesByTitle(etSearchStory.text.toString(), mSessionManager.defaultAddress)
            }
            SHOW_BY_SCENE -> {
                presenter.loadStoriesByScene(etSearchStory.text.toString(), mSessionManager.defaultAddress)
            }
        }
    }
}