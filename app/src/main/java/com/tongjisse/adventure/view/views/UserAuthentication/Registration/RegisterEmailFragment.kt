package com.tongjisse.adventure.view.views.UserAuthentication.Registration


import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.UserInfo
import com.tongjisse.adventure.presenter.UserAuthentication.RegisterPresenter
import com.tongjisse.adventure.view.common.BaseFragmentWithPresenter
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.UserAuthenticatioon.RegisterView
import kotlinx.android.synthetic.main.fragment_register_email.*
import java.sql.SQLException
import java.util.regex.Pattern

class RegisterEmailFragment : BaseFragmentWithPresenter(), RegisterView {

    override val presenter by lazy { RegisterPresenter(this) }

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

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //no need here
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //no need here
            }
        }
        etEmail.addTextChangedListener(textWatcher)
    }

    /**
     * Email has not been used
     *
     * @param userInfo: UserInfo
     * @author Feifan Wang
     */
    override fun RegisterSuccess(userInfo: UserInfo?) {
        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        val registerPasswordFragment = RegisterPasswordFragment()
        fragmentTransaction.replace(R.id.progressFragment, registerPasswordFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    /**
     * Email has been used or SQL error
     *
     * @param error: SQLException
     * @author Feifan Wang
     */
    override fun RegisterFailed(error: SQLException?) {
        if (error == null) {
            context!!.toast("该邮箱已被注册", Toast.LENGTH_SHORT)
        } else {
            context!!.toast("注册失败，请记录报错信息:${error!!.message}")
        }
    }


    fun registrationProceed() {
        if (isValidEmail(etEmail.text.toString().trim { it <= ' ' })) {
            bRegProceed.isEnabled = true
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button)
            bRegProceed.setTextColor(Color.parseColor("#ff6666"))
            bRegProceed.setOnClickListener {
                EMAIL = etEmail.text.toString()
                presenter.checkEmail(EMAIL)
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
