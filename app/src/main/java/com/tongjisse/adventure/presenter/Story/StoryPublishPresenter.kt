package com.tongjisse.adventure.presenter.Story

import com.tongjisse.adventure.data.bean.StoryList
import com.tongjisse.adventure.model.dao.StoryListDao
import com.tongjisse.adventure.model.dao.UserInfoDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.Story.StoryPublishView
import java.sql.SQLException

/**
 * Add and update story, get user info in StoryPublishActivity
 *
 * @author Feifan Wang
 */
class StoryPublishPresenter(
        val view: StoryPublishView
) : BasePresenter() {
    val storyDao = StoryListDao()
    val userInfoDao = UserInfoDao()

    /**
     * 新增游记
     *
     * @param story: StoryList
     */
    fun addStory(story: StoryList) {
        try {
            storyDao.addStory(story)
            view.addStorySuccess()
        } catch (error: SQLException) {
            view.addStoryFailed(error)
        }
    }

    /**
     * 修改游记
     *
     * @param story: StoryList
     */
    fun updateStory(story: StoryList) {
        try {
            storyDao.updateStory(story)
            view.updateStorySuccess()
        } catch (error: SQLException) {
            view.updateStoryFailed(error)
        }
    }

    /**
     * 查询用户
     *
     * @param email: String
     */
    fun getUser(email: String) {
        try {
            var user = userInfoDao.queryInfoByEmail(email)
            view.getUserInfo(user)
        } catch (error: SQLException) {
            view.userSqlError(error)
        }
    }
}