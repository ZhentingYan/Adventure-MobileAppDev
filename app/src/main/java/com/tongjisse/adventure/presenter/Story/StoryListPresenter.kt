package com.tongjisse.adventure.presenter.Story

import com.tongjisse.adventure.model.dao.StoryListDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.Story.StoryListView
import java.sql.SQLException

class StoryListPresenter(
        val listView: StoryListView
) : BasePresenter() {
    val storyDao = StoryListDao()
    fun showStoryList(email: String) {
        try {
            var userStoryList = storyDao.queryStoryByEmail(email)
            if(userStoryList!=null)
                listView.getUserStoryListsSuccess(userStoryList)
            else listView.getUserStoryListsFailed(null)
        } catch (error: SQLException) {
            listView.getUserStoryListsFailed(error)
        }
    }
}