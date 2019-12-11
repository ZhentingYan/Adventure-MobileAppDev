package com.tongjisse.adventure.presenter.UserAuthentication

import com.lzy.imagepicker.bean.ImageItem
import com.tongjisse.adventure.data.bean.UserInfo
import com.tongjisse.adventure.model.dao.UserInfoDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.UserAuthenticatioon.RegisterView
import java.sql.SQLException

class RegisterPresenter(val view: RegisterView) : BasePresenter() {
    val userDao = UserInfoDao()

    fun registerUserInfo(firstName: String, lastName: String, password: String, emailAddress: String, phoneNum: String,age:Int,avatar:ImageItem) {
        val userInfo = UserInfo(firstName, lastName, password, emailAddress, phoneNum,age,avatar)
        try {
            userDao.addInfo(userInfo)
            view.RegisterSuccess(userInfo)
        } catch (error: SQLException) {
            view.RegisterFailed(error)
        }
    }

    /**
     * 检查邮箱是否已经被占用
     *
     * @param email: String
     * @author: Feifan Wang
     */
    fun checkEmail(email:String){
        try{
            val userInfo = userDao.queryInfoByEmail(email)
            if (userInfo == null){
                view.RegisterSuccess(userInfo)
            } else {
                view.RegisterFailed(null)
            }
        } catch (error:SQLException){
            view.RegisterFailed(error)
        }
    }
}