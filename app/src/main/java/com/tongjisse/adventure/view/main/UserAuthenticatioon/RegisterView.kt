package com.tongjisse.adventure.view.main.UserAuthenticatioon

import com.tongjisse.adventure.data.bean.UserInfo
import java.sql.SQLException

/**
 * @comment: params can be null sometimes
 * @modified: By Feifan Wang
 */
interface RegisterView {
    fun RegisterSuccess(userInfo: UserInfo?)
    fun RegisterFailed(error: SQLException?)
}