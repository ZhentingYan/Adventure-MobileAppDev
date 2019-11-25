package com.tongjisse.adventure.model.bean

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable


@DatabaseTable(tableName = "story")
class Story (
        @DatabaseField(columnName = "title")
        var title: String,
        @DatabaseField(columnName = "content")
        var content: String,
        @DatabaseField(columnName = "photo")
        var photo: String,
        @DatabaseField(columnName = "user", canBeNull=false)
        var user: UserInfo,
        @DatabaseField(columnName = "time")
        var time: String,
        @DatabaseField(columnName = "place")
        var place: String
){
    // OrmLite 必须有无参数构造函数
    constructor():this(
            title="",
            content = "",
            photo = "",
            user = UserInfo(),
            time = "",
            place = ""
    )
}