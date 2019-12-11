package com.tongjisse.adventure.data.bean

import android.widget.ImageView
import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lzy.imagepicker.bean.ImageItem
import java.io.Serializable

/**
 * Info of one single story
 *
 * @author Feifan Wang
 * @modified Add Serializable to fix bug when use Intent.putExtra("story",StoryList)
 */
@DatabaseTable(tableName = "story_list")
class StoryList(
        @DatabaseField(columnName = "id", id = true)
        var id: String,
        @DatabaseField(columnName = "title")
        var title: String,
        @DatabaseField(columnName = "content")
        var content: String,
        @DatabaseField(columnName = "photo", dataType = DataType.SERIALIZABLE)
        var photo: ImageItem,
        @DatabaseField(columnName = "user", canBeNull = false, foreign = true, foreignAutoRefresh = true)
        var user: UserInfo,
        @DatabaseField(columnName = "time")
        var time: String,
        @DatabaseField(columnName = "scene")
        var scene: String,
        @DatabaseField(columnName = "district")
        var district: String
) : Serializable {
    // OrmLite 必须有无参数构造函数
    constructor() : this(
            id = "???",
            title = "",
            content = "",
            photo = ImageItem(),
            user = UserInfo(),
            time = "",
            scene = "",
            district = ""
    )
}