package com.tongjisse.adventure.view.main.MyAdventure

import com.lzy.imagepicker.bean.ImageItem
import java.sql.SQLException

interface MyAdventureView {
    fun updateAvatarSuccess(avatar: ImageItem)
    fun updateAvatarFailed(error: SQLException)
    fun getAvatarSuccess(avatar: ImageItem)
    fun getAvatarFailed(error: SQLException)
}