package com.tongjisse.adventure.presenter.Story

import com.tongjisse.adventure.model.dao.StoryListDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.Story.StoryListView
import java.sql.SQLException

/**
 * Get stories in StoryListFragment
 *
 * @author Feifan Wang
 */
class StoryListPresenter(
        val listView: StoryListView
) : BasePresenter() {
    val storyDao = StoryListDao()

    /**
     * 通过地点获取本地所有游记故事，不限用户
     *
     * @param district: String
     */
    fun loadStoriesByDistrict(district: String) {
        try {
            var stories = storyDao.queryStoryByDistrict(district)
            if (stories != null)
                listView.getStoryListsSuccess(stories)
            else listView.getStoryListsFailed(null)
        } catch (error: SQLException) {
            listView.getStoryListsFailed(error)
        }
    }

    /**
     * 通过标题获取本地相关游记故事，不限用户
     *
     * @param title: String
     * @param district: String
     */
    fun loadStoriesByTitle(title: String, district: String) {
        try {
            var stories = storyDao.queryStoryByTitleWithDistrict(title, district)
            if (stories != null)
                listView.getStoryListsSuccess(stories)
            else listView.getStoryListsFailed(null)
        } catch (error: SQLException) {
            listView.getStoryListsFailed(error)
        }
    }

    /**
     * 通过游记中提到的景点项获取本地相关游记故事，不限用户
     *
     * @param title: String
     * @param district: String
     */
    fun loadStoriesByScene(scene: String, district: String) {
        try {
            var stories = storyDao.queryStoryBySceneWithDistrict(scene, district)
            if (stories != null)
                listView.getStoryListsSuccess(stories)
            else listView.getStoryListsFailed(null)
        } catch (error: SQLException) {
            listView.getStoryListsFailed(error)
        }
    }
}