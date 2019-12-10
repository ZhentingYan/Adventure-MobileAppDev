package com.tongjisse.adventure.presenter.Story

import com.tongjisse.adventure.data.bean.StoryList
import com.tongjisse.adventure.data.bean.UserInfo
import com.tongjisse.adventure.data.bean.WishList
import com.tongjisse.adventure.data.plusAssign
import com.tongjisse.adventure.model.dao.StoryListDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.Story.StoryDetailView
import java.sql.SQLException

class StoryDetailPresenter(
        val view: StoryDetailView
) : BasePresenter() {
    var storyListDao = StoryListDao()

    fun delStoryList(storyList: StoryList) {
        try {
            storyListDao.delInfo(storyList)
            view.deleteStoryListSuccess(storyList)
        } catch (error: SQLException) {
            view.showSqlError(error)
        }
    }

    fun getStory(id : String){
        try {
            var story = storyListDao.queryStoryById(id)
            view.getStorySuccess(story)
        } catch (error: SQLException){
            view.showSqlError(error)
        }
    }

}