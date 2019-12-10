package com.tongjisse.adventure.view.main.Story

import com.tongjisse.adventure.data.bean.UserInfo
import java.sql.SQLException

interface StoryPublishView {
    fun addStorySuccess()
    fun addStoryFailed(error:Throwable)
    fun getUserInfo(user:UserInfo)
    fun userSqlError(error: SQLException)
}