package com.tongjisse.adventure.view.views.Story

import android.nfc.Tag
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.view.Window
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.StoryList
import com.tongjisse.adventure.presenter.Story.UserStoryPresenter
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.BaseActivityWithPresenter
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.MainListAdapter
import com.tongjisse.adventure.view.main.Story.StoryListAdapter
import com.tongjisse.adventure.view.main.Story.UserStoryView
import kotlinx.android.synthetic.main.activity_owned_stories.*
import kotlinx.android.synthetic.main.activity_owned_stories.ErrorLayout
import kotlinx.android.synthetic.main.activity_owned_stories.tvError
import java.sql.SQLException

class UserStoryActivity : BaseActivityWithPresenter(),UserStoryView{

    private lateinit var mSessionManager: SessionManager
    override val presenter by lazy { UserStoryPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_owned_stories)
        rvListing.layoutManager = GridLayoutManager(this, 1)
        mSessionManager = SessionManager(applicationContext)
        presenter.loadUserStoryList(mSessionManager.email)
        srStories.setOnRefreshListener {
            srStories.isRefreshing = true
            presenter.loadUserStoryList(mSessionManager.email)
        }
    }

    override fun getUserStoryListsSuccess(userStoryLists: List<StoryList>) {
        srStories.isRefreshing = false
        if (userStoryLists.isNotEmpty()) {
            ErrorLayout.visibility = View.GONE
            srStories.visibility = View.VISIBLE
            val categoryItemAdapters = userStoryLists.map(::StoryListAdapter)
            rvListing.adapter = MainListAdapter(categoryItemAdapters)
        } else {
            ErrorLayout.visibility = View.VISIBLE
            srStories.visibility = View.GONE
            tvError.text = "你还没有游记哦......快记录旅行途中有趣的故事吧！"
        }
    }

    override fun getUserStoryListsFailed(error: SQLException?) {
        if (error != null) {
            ErrorLayout.visibility = View.VISIBLE
            rvListing.visibility = View.GONE
            tvError.text = "加载游记失败......"
            application.toast("加载游记失败，错误信息:${error.message}")
        } else {
            ErrorLayout.visibility = View.VISIBLE
            rvListing.visibility = View.GONE
            tvError.text = "你还没有游记哦......快记录旅行途中有趣的故事吧！"
        }
    }
}