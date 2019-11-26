package com.tongjisse.adventure.view.views.UserAuthentication.Login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tongjisse.adventure.R
import com.tongjisse.adventure.dao.UserInfoDao
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.views.Main.MenuActivity
import kotlinx.android.synthetic.main.fragment_login.*


class WelcomeLoginFragment : android.support.v4.app.Fragment() {
    private var userDao= UserInfoDao()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                logInProceed()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        }
        etEmail.addTextChangedListener(textWatcher)
        etPassword.addTextChangedListener(textWatcher)
        bRegProceed.setOnClickListener {
            if(check(etEmail.text.toString(),etPassword.text.toString())) {
                //Session部分暂时去除，没有用户验证
                //val sessionManager = SessionManager(context)
                //sessionManager.createLoginSession(body!!.getUserId(), body!!.getEmail(), body!!.getFirstName(), body!!.getLastName(), body!!.getPhoneNum())
                val intent = Intent(activity, MenuActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                Toast.makeText(activity, "登录成功，欢迎探索Adventure", Toast.LENGTH_LONG).show()
            }
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

    fun check(email:String,psw:String):Boolean{
        val userInfo = userDao.queryInfoByEmail(email)
        if(userInfo==null){
            Toast.makeText(context,"邮箱未注册，请检查输入", Toast.LENGTH_LONG).show()
            return false
        } else if(userInfo.password!=psw){
            Toast.makeText(context,"密码与邮箱不匹配，请检查输入", Toast.LENGTH_LONG).show()
            return false
        } else {
            val sessionManager= SessionManager(context)
            sessionManager.createLoginSession(userInfo.emailAddress,userInfo.firstName,userInfo.lastName,userInfo.phoneNum)
            return true
        }
    }


}

