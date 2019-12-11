package com.tongjisse.adventure.presenter.Story

import com.tongjisse.adventure.model.dao.StoryListDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.Story.StoryListView
import java.sql.SQLException

class StoryListPresenter(
        val listView: StoryListView
) : BasePresenter() {
    val storyDao = StoryListDao()

    fun loadStoriesByDistrict(district: String){
        try{
            var stories = storyDao.queryStoryByDistrict(district)
            if (stories != null)
                listView.getStoryListsSuccess(stories)
            else listView.getStoryListsFailed(null)
        }catch (error: SQLException) {
            listView.getStoryListsFailed(error)
        }
    }

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