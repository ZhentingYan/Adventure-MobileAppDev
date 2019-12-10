package com.tongjisse.adventure.view.main.Story

import com.tongjisse.adventure.data.bean.StoryList
import java.sql.SQLException

interface StoryDetailView {
    // var refresh:Boolean
    fun show()
    fun showError(error: Throwable)
    fun getStorySuccess(detail: StoryList)
    fun deleteStoryListSuccess(detail: StoryList)
    fun addStorySuccess(detail: StoryList)
    fun showSqlError(error: SQLException)
}
