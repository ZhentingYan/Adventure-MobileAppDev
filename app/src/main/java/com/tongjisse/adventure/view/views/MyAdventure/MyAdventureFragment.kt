package com.tongjisse.adventure.view.views.MyAdventure

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tongjisse.adventure.R
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.Story.UserStoryView
import com.tongjisse.adventure.view.views.Main.MenuActivity
import com.tongjisse.adventure.view.views.Story.UserStoryActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class MyAdventureFragment : Fragment() {
    lateinit var mSessionManager: SessionManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSessionManager=SessionManager(context)
        tvName.text="${mSessionManager.firstName} ${mSessionManager.lastName}"
        llLogOut.setOnClickListener{
            mSessionManager.logoutUser()
        }
        llMyStory.setOnClickListener {
            val intent = Intent(context, UserStoryActivity::class.java)
            startActivity(intent)
        }
        llShake.setOnClickListener {
            MenuActivity.sectionTab.getTabAt(1)!!.select();
            context!!.toast("试试摇一摇你的手机，会有适合的景点推荐给你哦！")
        }
        llMyWishList.setOnClickListener {
            MenuActivity.sectionTab.getTabAt(3)!!.select();
        }
        tvEdit.setOnClickListener {
            val intent = Intent(it.context, UserProfileActivity::class.java)
            it.context.startActivity(intent)
        }
    }
}