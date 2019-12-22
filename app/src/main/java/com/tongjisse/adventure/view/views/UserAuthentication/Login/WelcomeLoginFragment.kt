package com.tongjisse.adventure.view.views.UserAuthentication.Login

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
import com.tongjisse.adventure.presenter.UserAuthentication.WelcomeLoginPresenter
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.BaseFragmentWithPresenter
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.UserAuthenticatioon.WelcomeLoginView
import com.tongjisse.adventure.view.views.Main.MenuActivity
import kotlinx.android.synthetic.main.fragment_login.*


class WelcomeLoginFragment : BaseFragmentWithPresenter(), WelcomeLoginView {
    override val presenter by lazy { WelcomeLoginPresenter(this) }
    lateinit var mSessionManager: SessionManager
    override fun LoginSuccess(userInfo: UserInfo) {
        mSessionManager.createLoginSession(userInfo.emailAddress, userInfo.firstName, userInfo.lastName, userInfo.phoneNum, userInfo.age, userInfo.password)
        val intent = Intent(activity, MenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        context!!.toast("${mSessionManager.firstName + mSessionManager.lastName}，欢迎探索Adventure")
    }

    override fun LoginFailed(state: Int) {
        if (state == 0)
            context!!.toast("邮箱未注册，请检查输入")
        else context!!.toast("密码与邮箱不匹配，请检查输入")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        mSessionManager = SessionManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                logInProceed()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // no need here
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // no need here
            }
        }
        etEmail.addTextChangedListener(textWatcher)
        etPassword.addTextChangedListener(textWatcher)
        bRegProceed.setOnClickListener {
            presenter.checkUserValidation(etEmail.text.toString(), etPassword.text.toString())
        }
    }


    fun logInProceed() {
        if (etEmail.text.toString().isEmpty() || etPassword.text.toString().isEmpty()) {
            bRegProceed.isEnabled = false
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button_fail)
            bRegProceed.setTextColor(Color.parseColor("#ff6666"))
        } else {
            bRegProceed.isEnabled = true
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button)
            bRegProceed.setTextColor(Color.parseColor("#ff6666"))

        }
    }
}

