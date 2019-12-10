package com.tongjisse.adventure.view.views.Story

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.j256.ormlite.stmt.query.In
import com.lljjcoder.bean.DistrictBean
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.StoryList
import com.tongjisse.adventure.presenter.Story.StoryListPresenter
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
    lateinit var mSessionManager: SessionManager
    override val presenter by lazy { StoryListPresenter(this) }

    override fun getUserStoryListsSuccess(userStoryLists: List<StoryList>) {
        if (userStoryLists != null && userStoryLists.isNotEmpty()) {
            ErrorLayout.visibility = View.GONE
            storyRecyclerView.visibility = View.VISIBLE
            Log.d(tag,"is not empty?")
            val categoryItemAdapters = userStoryLists.map(::StoryListAdapter)
            storyRecyclerView.adapter = MainListAdapter(categoryItemAdapters)
        } else {
            ErrorLayout.visibility = View.VISIBLE
            storyRecyclerView.visibility = View.GONE
            tvError.text = "你还没有游记哦......快记录旅行途中有趣的故事吧！"
        }
    }

    override fun getUserStoryListsFailed(error: SQLException?) {
        if(error!=null){
            ErrorLayout.visibility = View.VISIBLE
            storyRecyclerView.visibility = View.GONE
            tvError.text = "加载游记失败......"
            context!!.toast("加载游记失败，错误信息:${error.message}")
        }else{
            ErrorLayout.visibility = View.VISIBLE
            storyRecyclerView.visibility = View.GONE
            tvError.text = "在${mSessionManager.district}还没有游记哦～"
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_story, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        storyRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mSessionManager = SessionManager(context)
        presenter.showStoryList(mSessionManager.email)

        swipeRefreshView.setOnRefreshListener {
            swipeRefreshView.isRefreshing = true
            presenter.showStoryList(mSessionManager.email)
            swipeRefreshView.isRefreshing = false
        }

        bPublish.setOnClickListener(){
            val intent = Intent(context, StoryPublishActivity::class.java)
            intent.putExtra("email",mSessionManager.email)
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1->{
                if(resultCode==RESULT_OK){
                    if(data!!.getBooleanExtra("isSucceed",true)){

                    }
                }
            }
        }
    }
}