package com.tongjisse.adventure.view.views.UserAuthentication.Registration


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tongjisse.adventure.R
import kotlinx.android.synthetic.main.fragment_password.*
import java.util.regex.Pattern


class RegisterPasswordFragment : Fragment() {
    /**
     * View Initialization
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_password, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                registrationProceed()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // no need here
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // no need here
            }
        }
        etPassword.addTextChangedListener(textWatcher)
    }

    /**
     * This Function is called when user input sth
     * @author ZhentingYan
     */
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

    /**
     * Validate password
     * @param password：String
     * @return Boolean(Valid/Invalid)
     * @author ZhentingYan
     */
    fun isValidPassword(password: String): Boolean {
        val ALPHANUMERICAL_PATTERN = "[^\\w\\d]*(([0-9]+.*[A-Za-z]+.*)|[A-Za-z]+.*([0-9]+.*))"
        val pattern = Pattern.compile(ALPHANUMERICAL_PATTERN)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    companion object {
        lateinit var PASSWORD: String
    }
}
