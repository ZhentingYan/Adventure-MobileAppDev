package com.tongjisse.adventure.data.bean

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

/**
 * Info of one single wish
 *
 * @author Feifan Wang
 */
@DatabaseTable(tableName = "wish_list")
class WishList(
        @DatabaseField(generatedId = true)
        var id: Int,
        @DatabaseField(columnName = "user", canBeNull = false)
        var user: String,
        @DatabaseField(columnName = "scene", canBeNull = false)
        var scene: String,
        @DatabaseField(columnName = "state", canBeNull = false)
        var state: Int, //0表示未完成，1表示已完成
        @DatabaseField(columnName = "poi", canBeNull = false)
        var poi: String,
        @DatabaseField(columnName = "photo", canBeNull = false)
        var photo: String,
        @DatabaseField(columnName = "district", canBeNull = false)
        var district: String
) : Serializable {
    // OrmLite 必须有无参数构造函数
    constructor() : this(
            id = 0,
            user = "",
            scene = "",
            state = 0,
            poi = "",
            photo = "",
            district = ""
    )
}