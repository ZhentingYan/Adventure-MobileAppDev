package com.tongjisse.adventure.presenter.MyAdventure

import com.tongjisse.adventure.data.bean.UserInfo
import com.tongjisse.adventure.model.dao.UserInfoDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.MyAdventure.ProfileView
import java.sql.SQLException

class UserProfilePresenter (val view: ProfileView):BasePresenter(){
    var mUserInfoDao=UserInfoDao()
    fun updateUserProfile(email:String,userInfo:UserInfo){
        try {
            var tempUserInfo=mUserInfoDao.queryInfoByEmail(email)
            tempUserInfo.phoneNum=userInfo.phoneNum
            tempUserInfo.firstName=userInfo.firstName
            tempUserInfo.lastName=userInfo.lastName
            tempUserInfo.age=userInfo.age
            tempUserInfo.password=userInfo.password
            mUserInfoDao.updateInfo(tempUserInfo)
            view.showUpdateProfileSuccess()
        }catch (error:SQLException){
            view.showUpdateProfileFailure(error)
        }
    }
}