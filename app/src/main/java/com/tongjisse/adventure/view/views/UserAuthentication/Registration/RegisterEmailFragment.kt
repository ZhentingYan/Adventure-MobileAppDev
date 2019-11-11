package com.tongjisse.adventure.view.views.UserAuthentication.Registration


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
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

import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlinx.android.synthetic.main.fragment_register_email.*



class RegisterEmailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register_email, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textWatcher = object : TextWatcher {
            //Check if email is valid
            override fun afterTextChanged(s: Editable) {
                registrationProceed()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        }
        etEmail.addTextChangedListener(textWatcher)
    }

    fun registrationProceed() {
        if (isValidEmail(etEmail.text.toString().trim { it <= ' ' })) {
            bRegProceed.isEnabled = true
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button)
            bRegProceed.setTextColor(Color.parseColor("#ff6666"))
            bRegProceed.setOnClickListener {


                EMAIL = etEmail.text.toString()
                val fragmentManager = fragmentManager
                val fragmentTransaction = fragmentManager!!.beginTransaction()
                val registerPasswordFragment = RegisterPasswordFragment()
                fragmentTransaction.replace(R.id.progressFragment, registerPasswordFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()


            }
        } else {
            bRegProceed.isEnabled = false
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button_fail)
            bRegProceed.setTextColor(Color.parseColor("#ff6666"))
        }
    }

    companion object {
        lateinit var EMAIL: String

        fun isValidEmail(email: String): Boolean {
            val EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            val pattern = Pattern.compile(EMAIL_PATTERN)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }
    }
}
