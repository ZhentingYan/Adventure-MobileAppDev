package com.tongjisse.adventure.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.tongjisse.adventure.view.views.Welcome.WelcomeActivity

class SessionManager(private val context: Context?) {
    private val sessionSP: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val IS_LOGIN = "IS_LOGIN"

    val isLoggedIn: Boolean
        get() = sessionSP.getBoolean(IS_LOGIN, false)

    init {
        sessionSP = this.context!!.getSharedPreferences(SESSION_SP, Context.MODE_PRIVATE)
        editor = sessionSP.edit()
    }

    fun createLoginSession(id: Int, email: String, firstName: String, lastName: String, phoneNum: String) {
        editor.putBoolean(IS_LOGIN, true)

        editor.putString(EMAIL, email)

        editor.putString(FIRST_NAME, firstName)

        editor.putString(LAST_NAME, lastName)

        editor.putString(PHONE_NUM, phoneNum)

        editor.apply()
    }

    fun logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear()
        editor.apply()
        // After logout redirect user to Loing Activity
        val intent = Intent(context, WelcomeActivity::class.java)
        // Add new Flag to start new Activity
        // Closing all the Activities
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        // Staring Login Activity
        context!!.startActivity(intent)
    }

    fun checkLogin() {
        // Check login status
        if (isLoggedIn) {
            //Loading Menu Activity未完成
            /*
            val intent = Intent(context, LoadingMenuActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
            */
        }
    }

    companion object {
        val SESSION_SP = "SESSION_SP"
        val EMAIL = "EMAIL"
        val FIRST_NAME = "FIRST_NAME"
        val LAST_NAME = "LAST_NAME"
        val PHONE_NUM = "PHONE_NUM"
    }
}
