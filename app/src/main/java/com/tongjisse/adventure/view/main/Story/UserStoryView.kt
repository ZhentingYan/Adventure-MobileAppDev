package com.tongjisse.adventure.view.main.Story

import com.tongjisse.adventure.data.bean.StoryList
import java.sql.SQLException

interface UserStoryView {
    fun getUserStoryListsSuccess(userStoryLists: List<StoryList>)
    fun getUserStoryListsFailed(error: SQLException?)
}