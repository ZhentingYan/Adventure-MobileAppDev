package com.tongjisse.adventure.view.main.UserAuthenticatioon

import com.tongjisse.adventure.data.bean.UserInfo

interface WelcomeLoginView {
    fun LoginSuccess(userInfo: UserInfo)
    fun LoginFailed(state: Int)
}