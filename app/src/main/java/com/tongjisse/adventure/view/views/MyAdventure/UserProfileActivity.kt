package com.tongjisse.adventure.view.views.MyAdventure

import android.os.Bundle
import android.view.Window
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.UserInfo
import com.tongjisse.adventure.presenter.MyAdventure.UserProfilePresenter
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.BaseActivityWithPresenter
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.MyAdventure.ProfileView
import kotlinx.android.synthetic.main.user_profile_edit_activity.*
import java.sql.SQLException

class UserProfileActivity : BaseActivityWithPresenter(), ProfileView {
    override val presenter by lazy { UserProfilePresenter(this) }
    lateinit var mSessionManager: SessionManager
    override fun showUpdateProfileFailure(error: SQLException) {
        applicationContext.toast("更新用户信息时出错,错误信息:${error.message}")
        this.finish()
    }

    override fun showUpdateProfileSuccess() {
        mSessionManager.createLoginSession(mSessionManager.email, etFirstName.text.toString(), etLastName.text.toString(), etPhoneNum.text.toString(), etAge.text.toString().toInt(), etPassword.text.toString())
        tvUserName.text = "${mSessionManager.firstName} ${mSessionManager.lastName}"
        etPassword.isEnabled = false
        etFirstName.isEnabled = false
        etLastName.isEnabled = false
        etPhoneNum.isEnabled = false
        etAge.isEnabled = false
        bNext.text = "编辑 >"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.user_profile_edit_activity)
        mSessionManager = SessionManager(applicationContext)
        tvUserName.text = "${mSessionManager.firstName} ${mSessionManager.lastName}"
        etPassword.setText(mSessionManager.password)
        etEmail.setText(mSessionManager.email)
        etFirstName.setText(mSessionManager.firstName)
        etLastName.setText(mSessionManager.lastName)
        etPhoneNum.setText(mSessionManager.phoneNum)
        bNext.setOnClickListener {
            if (bNext.text.equals("编辑 >")) {
                etPassword.isEnabled = true
                etFirstName.isEnabled = true
                etLastName.isEnabled = true
                etPhoneNum.isEnabled = true
                etAge.isEnabled = true
                bNext.text = "保存 >"
            } else {
                var userInfo = UserInfo()
                userInfo.firstName = etFirstName.text.toString()
                userInfo.lastName = etLastName.text.toString()
                userInfo.phoneNum = etPhoneNum.text.toString()
                userInfo.password = etPassword.text.toString()
                userInfo.age = etAge.text.toString().toInt()
                presenter.updateUserProfile(mSessionManager.email, userInfo)

            }
        }

    }

}