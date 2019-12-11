package com.tongjisse.adventure.view.main.Story

import com.tongjisse.adventure.data.bean.StoryList
import java.sql.SQLException

interface StoryListView {
    fun getStoryListsSuccess(userStoryLists: List<StoryList>)
    fun getStoryListsFailed(error: SQLException?)
}