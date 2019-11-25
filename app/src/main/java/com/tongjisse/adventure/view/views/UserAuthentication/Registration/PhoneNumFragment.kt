package com.tongjisse.adventure.view.views.UserAuthentication.Registration

import android.content.Intent
import android.widget.Toast
import java.util.regex.Pattern

import android.graphics.Color
import android.location.Address
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.telephony.PhoneNumberUtils
import android.telephony.SmsManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.tongjisse.adventure.R
import com.tongjisse.adventure.dao.UserInfoDao
import com.tongjisse.adventure.model.bean.UserInfo
import com.tongjisse.adventure.view.views.Main.MenuActivity

import java.util.Random
import kotlinx.android.synthetic.main.fragment_phone_num.*


/**
 * Created by Owner on 2017-07-21.
 */

class PhoneNumFragment : Fragment() {
    private val userDao=UserInfoDao();


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

    private fun addNewUserInfo(firstName:String,lastName:String,password:String,emailAddress: String,phoneNum: String) {
        val userInfo=UserInfo(firstName,lastName,password,emailAddress,phoneNum,null)
        userDao.addInfo(userInfo)
    }

    /*
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val basicProgressBar = activity!!.findViewById(R.id.basicProgressBar) as ProgressBar
            if (basicProgressBar != null) {
                basicProgressBar.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.lightRed))
                basicProgressBar.progress = 100
            }

        }
    */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phone_num, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bRegProceed.setOnClickListener {
            //Generate random num
            PHONENUM = etPhone.text.toString()
            // Add to database
            addNewUserInfo(RegisterNameFragment.FIRST_NAME,RegisterNameFragment.LAST_NAME,
                    RegisterPasswordFragment.PASSWORD,RegisterEmailFragment.EMAIL, PHONENUM)
            val intent = Intent(activity, MenuActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            Toast.makeText(activity, "注册成功，欢迎探索Adventure！", Toast.LENGTH_LONG).show()

        }


        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                proceed()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        }
        etPhone.addTextChangedListener(textWatcher)
    }

    companion object {
        lateinit var PHONENUM: String

        fun isValidPhoneNum(PHONENUM: String): Boolean {
            val PHONE_PATTERN = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$"
            val pattern = Pattern.compile(PHONE_PATTERN)
            val matcher = pattern.matcher(PHONENUM)
            return matcher.matches()
        }
    }


}