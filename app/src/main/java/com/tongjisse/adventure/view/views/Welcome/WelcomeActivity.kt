package com.tongjisse.adventure.view.views.Welcome

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tongjisse.adventure.R
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.views.UserAuthentication.Registration.RegisterNameFragment

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val sessionManager = SessionManager(this)
        sessionManager.checkLogin()
        supportFragmentManager.beginTransaction().replace(R.id.progressFragment, WelcomeFragment()).commit()

        //        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        //        Typeface openSans = Typeface.createFromAsset(getAssets(), "fonts.opensans/OpenSans-Regular.ttf");
        //        tvWelcome.setTypeface(openSans);


    }
}
