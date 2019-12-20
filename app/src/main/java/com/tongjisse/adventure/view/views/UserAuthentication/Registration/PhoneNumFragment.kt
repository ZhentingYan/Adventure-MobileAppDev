package com.tongjisse.adventure.view.views.UserAuthentication.Registration

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.UserInfo
import com.tongjisse.adventure.presenter.UserAuthentication.RegisterPresenter
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.BaseFragmentWithPresenter
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.UserAuthenticatioon.RegisterView
import com.tongjisse.adventure.view.views.Main.MenuActivity
import kotlinx.android.synthetic.main.fragment_phone_num.*
import java.sql.SQLException
import java.util.regex.Pattern


/**
 * Created by Owner on 2017-07-21.
 */

class PhoneNumFragment : BaseFragmentWithPresenter(), RegisterView {
    override val presenter by lazy { RegisterPresenter(this) }
    lateinit var mSessionManager: SessionManager

    override fun RegisterFailed(error: SQLException?) {
        context!!.toast("注册失败，请记录报错信息:${error!!.message}")
    }

    override fun RegisterSuccess(userInfo: UserInfo?) {
        mSessionManager.createLoginSession(userInfo!!.emailAddress, userInfo.firstName, userInfo.lastName, userInfo.phoneNum,userInfo.age,userInfo.password)
        val intent = Intent(activity, MenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        context!!.toast("${userInfo.firstName + userInfo.lastName},欢迎探索Adventure!")
    }

    fun proceed() {
        if (etPhone.text.length > 10) {
            if (isValidPhoneNum(etPhone.text.toString())) {
                bRegProceed.isEnabled = true
                bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button)
                bRegProceed.setTextColor(Color.parseColor("#ff6666"))
            }

        } else {
            bRegProceed.isEnabled = false
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button_fail)
            bRegProceed.setTextColor(Color.parseColor("#ff6666"))
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phone_num, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSessionManager = SessionManager(context)
        bRegProceed.setOnClickListener {
            //Generate random num
            PHONENUM = etPhone.text.toString()
            // Add to database
            presenter.registerUserInfo(
                    RegisterNameFragment.FIRST_NAME,
                    RegisterNameFragment.LAST_NAME,
                    RegisterPasswordFragment.PASSWORD,
                    RegisterEmailFragment.EMAIL,
                    PHONENUM,
                    RegisterAgeFragment.AGE,
                    RegisterAvatarFragment.AVATAR)
        }

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                proceed()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // no need here
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // no need here
            }
        }
        etPhone.addTextChangedListener(textWatcher)
    }

    fun isValidPhoneNum(PHONENUM: String): Boolean {
        val PHONE_PATTERN = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$"
        val pattern = Pattern.compile(PHONE_PATTERN)
        val matcher = pattern.matcher(PHONENUM)
        return matcher.matches()
    }

    companion object {
        lateinit var PHONENUM: String
    }
}