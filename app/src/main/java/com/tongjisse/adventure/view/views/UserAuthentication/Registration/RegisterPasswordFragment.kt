package com.tongjisse.adventure.view.views.UserAuthentication.Registration


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout

import java.util.regex.Matcher
import java.util.regex.Pattern

import com.tongjisse.adventure.R
import kotlinx.android.synthetic.main.fragment_password.*

/**
 * Created by Owner on 2017-06-10.
 */

class RegisterPasswordFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_password, container, false)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                registrationProceed()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        }
        etPassword.addTextChangedListener(textWatcher)

    }

    fun registrationProceed() {
        //errorLayout uses elevation attribute (API 21 and above, check for optimizations later)
        errorLayout.visibility = View.INVISIBLE
        if (etPassword.text.toString().trim { it <= ' ' }.length >= 8) {
            if (isValidPassword(etPassword.text.toString())) {
                bRegProceed.isEnabled = true
                //if statement added here to prevent REGEX operation from executing every text change
                bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button)
                bRegProceed.setTextColor(Color.parseColor("#ff6666"))
                bRegProceed.setOnClickListener {
                    PASSWORD = etPassword.text.toString()
                    val fragmentManager = fragmentManager
                    val fragmentTransaction = fragmentManager!!.beginTransaction()
                    val registerAgeFragment = RegisterAgeFragment()
                    fragmentTransaction.replace(R.id.progressFragment, registerAgeFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            } else {
                bRegProceed.isEnabled = false
                errorLayout.visibility = View.VISIBLE
            }
        } else {
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button_fail)
            bRegProceed.setTextColor(Color.parseColor("#ff6666"))
        }
    }

    companion object {
        lateinit var PASSWORD: String

        fun isValidPassword(password: String): Boolean {
            val ALPHANUMERICAL_PATTERN = "[^\\w\\d]*(([0-9]+.*[A-Za-z]+.*)|[A-Za-z]+.*([0-9]+.*))"
            val pattern = Pattern.compile(ALPHANUMERICAL_PATTERN)
            val matcher = pattern.matcher(password)
            return matcher.matches()
        }
    }
}
