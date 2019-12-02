package com.tongjisse.adventure.presenter.UserAuthentication

import com.tongjisse.adventure.data.bean.UserInfo
import com.tongjisse.adventure.model.dao.UserInfoDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.UserAuthenticatioon.RegisterView
import java.sql.SQLException

class RegisterPresenter(val view: RegisterView) : BasePresenter() {
    val userDao = UserInfoDao()

    fun registerUserInfo(firstName: String, lastName: String, password: String, emailAddress: String, phoneNum: String) {
        val userInfo = UserInfo(firstName, lastName, password, emailAddress, phoneNum)
        try {
            userDao.addInfo(userInfo)
            view.RegisterSuccess(userInfo)
        } catch (error: SQLException) {
            view.RegisterFailed(error)
        }
    }
}