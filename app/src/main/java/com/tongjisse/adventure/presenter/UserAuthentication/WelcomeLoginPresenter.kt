package com.tongjisse.adventure.presenter.UserAuthentication

import com.tongjisse.adventure.model.dao.UserInfoDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.UserAuthenticatioon.WelcomeLoginView

class WelcomeLoginPresenter(
        val view: WelcomeLoginView
) : BasePresenter() {
    val userDao = UserInfoDao()
    /**
     * Validate UserInfo when Login
     * @param email:String
     * @param password:String
     * @author ZhentingYan
     */
    fun checkUserValidation(email: String, password: String) {
        val userInfo = userDao.queryInfoByEmail(email)
        if (userInfo == null)
            view.LoginFailed(0)
        else {
            if (userInfo.password != password)
                view.LoginFailed(1)
            else view.LoginSuccess(userInfo)
        }
    }
}
