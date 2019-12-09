package com.tongjisse.adventure.presenter.Story

import com.tongjisse.adventure.data.bean.StoryList
import com.tongjisse.adventure.model.dao.StoryListDao
import com.tongjisse.adventure.model.dao.UserInfoDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.Story.StoryPublishView
import java.sql.SQLException

class StoryPublishPresenter(
        val view: StoryPublishView
) : BasePresenter() {
    val storyDao = StoryListDao()
    val userInfoDao = UserInfoDao()
    fun addStory(story:StoryList){
        try{
            storyDao.addStory(story)
            view.addStorySuccess()
        } catch(error:Throwable) {
            view.addStoryFailed(error)
        }
    }

    fun getUser(email:String){
        try{
            var user=userInfoDao.queryInfoByEmail(email)
            view.getUserInfo(user)
        } catch (error:SQLException){
            view.userSqlError(error)
        }
    }
}