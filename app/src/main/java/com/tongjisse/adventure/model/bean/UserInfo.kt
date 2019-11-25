package com.tongjisse.adventure.model.bean

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.*;


@DatabaseTable(tableName = "user_info")
class UserInfo(
        @DatabaseField(columnName = "firstName")
        var firstName: String,
        @DatabaseField(columnName = "lastName")
        var lastName: String,
        @DatabaseField(columnName = "password")
        var password: String,
        @DatabaseField(id=true,columnName = "emailAddress", canBeNull=false, unique = true)
        var emailAddress: String,
        @DatabaseField(columnName = "phoneNum")
        var phoneNum: String,
        @DatabaseField(columnName = "preference")
        var prefer: Array<String>?
) : Serializable{

        // OrmLite 必须有无参数构造函数
        constructor():this(
                firstName = "",
                lastName = "",
                password = "",
                emailAddress = "",
                phoneNum = "",
                prefer = null
        )
}