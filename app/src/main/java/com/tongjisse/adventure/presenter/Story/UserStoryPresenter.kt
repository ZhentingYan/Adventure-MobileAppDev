package com.tongjisse.adventure.presenter.Story

import com.tongjisse.adventure.model.dao.StoryListDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.Story.UserStoryView
import java.sql.SQLException

/**
 * Get all stories of user in UserStoryActivity
 *
 * @author Feifan Wang
 */
class UserStoryPresenter(
        val view: UserStoryView
) : BasePresenter() {
    val storyDao = StoryListDao()

    /**
     * 获取用户全部游记故事
     *
     */
    fun loadUserStoryList(email: String) {
        try {
            var userStoryList = storyDao.queryStoryByEmail(email)
            if (userStoryList != null)
                view.getUserStoryListsSuccess(userStoryList)
            else view.getUserStoryListsFailed(null)
        } catch (error: SQLException) {
            view.getUserStoryListsFailed(error)
        }
    }
}