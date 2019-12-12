package com.tongjisse.adventure.data.bean

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.lzy.imagepicker.bean.ImageItem
import java.io.Serializable

/**
 * Info of one single user
 *
 * @author Feifan Wang
 * @modified Add Serializable to fix bug when use Intent.putExtra("story",StoryList)
 */
@DatabaseTable(tableName = "user_info")
class UserInfo(
        @DatabaseField(columnName = "firstName")
        var firstName: String,
        @DatabaseField(columnName = "lastName")
        var lastName: String,
        @DatabaseField(columnName = "password")
        var password: String,
        @DatabaseField(id = true, columnName = "emailAddress", canBeNull = false, unique = true)
        var emailAddress: String,
        @DatabaseField(columnName = "phoneNum")
        var phoneNum: String,
        @DatabaseField(columnName = "age")
        var age: Int,
        @DatabaseField(columnName = "avatar", dataType = DataType.SERIALIZABLE)
        var avatar: ImageItem
) : Serializable {
    // OrmLite 必须有无参数构造函数
    constructor() : this(
            firstName = "",
            lastName = "",
            password = "",
            emailAddress = "",
            phoneNum = "",
            age = 0,
            avatar = ImageItem()
    )
}