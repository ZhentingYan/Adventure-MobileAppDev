package com.tongjisse.adventure.presenter.MyAdventure

import com.lzy.imagepicker.bean.ImageItem
import com.tongjisse.adventure.data.bean.UserInfo
import com.tongjisse.adventure.model.dao.UserInfoDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.MyAdventure.MyAdventureView
import java.sql.SQLException

/**
 * Get or update avatar in MyAdventureFragment
 *
 * @author Feifan Wang
 */
class MyAdventurePresenter(val view: MyAdventureView) : BasePresenter() {
    val userDao = UserInfoDao()

    /**
     * 更新用户头像
     *
     * @param avatar: ImageItem
     * @param email: String
     */
    fun updateAvatar(avatar: ImageItem, email: String) {
        try {
            var user = userDao.queryInfoByEmail(email)
            user.avatar = avatar
            userDao.updateInfo(user)
            view.updateAvatarSuccess(avatar)
        } catch (error: SQLException) {
            view.updateAvatarFailed(error)
        }
    }

    /**
     * 获取用户头像
     *
     * @param email: String
     */
    fun getAvatar(email: String) {
        try {
            val user = userDao.queryInfoByEmail(email)
            view.getAvatarSuccess(user.avatar)
        } catch (error: SQLException) {
            view.getAvatarFailed(error)
        }
    }
}