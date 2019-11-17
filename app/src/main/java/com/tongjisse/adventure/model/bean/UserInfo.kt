package com.tongjisse.adventure.model.bean

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable


@DatabaseTable(tableName = "user_info")
data class UserInfo (
        @DatabaseField(generatedId = true)//绑定字段,id 为 primary key，且自动生成
        val id: Long,
        @DatabaseField(columnName = "firstName")
        var firstName: String,
        @DatabaseField(columnName = "lastName")
        var lastName: String,
        @DatabaseField(columnName = "password")
        var password: String,
        @DatabaseField(columnName = "emailAddress", unique = true)
        var emailAddress: String,
        @DatabaseField(columnName = "phoneNum")
        var phoneNum: String
){
        // OrmLite 必须有无参数构造函数
        constructor():this(
                id=0,
                firstName = "",
                lastName = "",
                password = "",
                emailAddress = "",
                phoneNum = ""
        )
}