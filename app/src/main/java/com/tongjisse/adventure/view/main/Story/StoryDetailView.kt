package com.tongjisse.adventure.view.main.Story

import com.tongjisse.adventure.data.bean.StoryList
import java.sql.SQLException

interface StoryDetailView {
    // var refresh:Boolean
    fun getStorySuccess(detail: StoryList)
    fun getStoryFailed(error:SQLException?)
    fun delStorySuccess()
    fun delStoryFailed(error:SQLException)
    fun showSQLError(error: SQLException)
}
