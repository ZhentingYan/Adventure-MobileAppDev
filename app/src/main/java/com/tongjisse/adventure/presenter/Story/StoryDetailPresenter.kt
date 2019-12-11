package com.tongjisse.adventure.presenter.Story

import com.tongjisse.adventure.data.bean.StoryList
import com.tongjisse.adventure.data.bean.UserInfo
import com.tongjisse.adventure.data.bean.WishList
import com.tongjisse.adventure.data.plusAssign
import com.tongjisse.adventure.model.dao.StoryListDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.Story.StoryDetailView
import java.sql.SQLException

/**
 * Get or delete story in StoryDetailActivity
 *
 * @author Feifan Wang
 */
class StoryDetailPresenter(
        val view: StoryDetailView
) : BasePresenter() {
    var storyListDao = StoryListDao()

    /**
     * 删除游记故事
     *
     * @param storyList: StoryList
     */
    fun delStory(storyList: StoryList) {
        try {
            storyListDao.delStory(storyList)
            view.delStorySuccess()
        } catch (error: SQLException) {
            view.delStoryFailed(error)
        }
    }

    /**
     * 获取游记故事
     *
     * @param id: String
     */
    fun getStory(id: String) {
        try {
            var story = storyListDao.queryStoryById(id)
            if (story != null) {
                view.getStorySuccess(story)
            } else {
                view.getStoryFailed(null)
            }
        } catch (error: SQLException) {
            view.showSqlError(error)
        }
    }

}