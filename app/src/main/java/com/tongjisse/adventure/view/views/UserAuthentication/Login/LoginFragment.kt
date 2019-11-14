package com.tongjisse.adventure.view.views.UserAuthentication.Login

import android.app.Activity
import android.se.omapi.Session
import com.tongjisse.adventure.utils.SessionManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.tongjisse.adventure.R
import com.tongjisse.adventure.view.views.Main.MenuActivity
import kotlinx.android.synthetic.main.fragment_login.*


class LogInFragment : android.support.v4.app.Fragment() {

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
            bRegProceed.setOnClickListener {
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
}


