package com.tongjisse.adventure.view.views.Main

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tongjisse.adventure.R
import com.tongjisse.adventure.view.views.Explore.ExploreFragment
import com.tongjisse.adventure.view.views.MyAdventure.MyAdventureFragment
import com.tongjisse.adventure.view.views.ScenicSpot.ScenicSpotFragment
import com.tongjisse.adventure.view.views.Story.StoryListFragment
import com.tongjisse.adventure.view.views.WishList.WishListFragment
import kotlinx.android.synthetic.main.tab_item.view.*


class MenuActivity : AppCompatActivity() {
    // internal var exploreFragment: ExploreFragment
    lateinit var thisFragment: Fragment

    companion object {
        lateinit var sectionTab: TabLayout
    }

    private fun changeTabsFont(tabLayout: TabLayout) {
        val vg = tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildsCount = vgTab.childCount
            for (i in 0 until tabChildsCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    tabViewChild.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35f)
                }
            }
        }
    }

    /*
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.sectionFragmentReplace)

        if (currentFragment is LocationFragment || currentFragment is SearchGuestFragment) {

            super.onBackPressed()
            //section tab is invisible when one of the search-bar filters are clicked
            findViewById(R.id.sectionTab).setVisibility(View.VISIBLE)
        } else if (currentFragment is ExploreFragment || currentFragment is ProfileFragment) {
            finish()
        } else {
            super.onBackPressed()
        }

    }
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

/*
        val profileFragment = ProfileFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.sectionFragmentReplace, exploreFragment, "exploreFragment")

        fragmentTransaction.hide(profileFragment)
        fragmentTransaction.show(exploreFragment)
        fragmentTransaction.commit()
*/
        sectionTab = findViewById(R.id.sectionTab) as TabLayout

        val tabName = arrayOf("探索", "景点", "故事", "心愿单", "我的")
        val tabPics = arrayOf(R.drawable.explore_logo, R.drawable.scene_logo, R.drawable.story_logo, R.drawable.likes_logo, R.drawable.profile_logo)
        // sectionTab.addTab(sectionTab.newTab().setText("Explore"))
        // sectionTab.addTab(sectionTab.newTab().setText("Profile"))
        //changeTabsFont(sectionTab)
        for (i in 0..4) {
            val tab = sectionTab.newTab()
            val view = LayoutInflater.from(this).inflate(R.layout.tab_item, null)
            val tv = view.findViewById(R.id.tvSection) as TextView
            tv.setText(tabName[i])
            val img = view.findViewById(R.id.ivIcon) as ImageView
            img.setImageResource(tabPics[i])
            tab.setCustomView(view)
            sectionTab.addTab(tab)
            if (i == 0) {
                tv.setTextColor(Color.parseColor("#FF6666"))
            }
        }
        sectionTab.getTabAt(0)!!.select();
        val scenicSpotFragment = ScenicSpotFragment()
        val myAdventureFragment = MyAdventureFragment()
        val wishListFragment = WishListFragment()
        val storyListFragment = StoryListFragment()
        val exploreFragment = ExploreFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.sectionFragmentReplace, exploreFragment, "exploreFragment")
        fragmentTransaction.hide(scenicSpotFragment)
        fragmentTransaction.hide(myAdventureFragment)
        fragmentTransaction.show(exploreFragment)
        fragmentTransaction.commit()
        thisFragment = exploreFragment
        sectionTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {
                    4 //Explore
                    -> {
                        if (!myAdventureFragment.isAdded()) {
                            fragmentManager.beginTransaction().add(R.id.sectionFragmentReplace, myAdventureFragment).commit()
                        }
                        fragmentManager.beginTransaction().hide(thisFragment)
                                .show(myAdventureFragment).commit()
                        thisFragment = myAdventureFragment
                    }
                    3 //Explore
                    -> {
                        if (!wishListFragment.isAdded()) {
                            fragmentManager.beginTransaction().add(R.id.sectionFragmentReplace, wishListFragment).commit()
                        }
                        fragmentManager.beginTransaction().hide(thisFragment)
                                .show(wishListFragment).commit()
                        thisFragment = wishListFragment
                    }
                    2 // StoryList
                    -> {
                        if (!storyListFragment.isAdded()) {
                            fragmentManager.beginTransaction().add(R.id.sectionFragmentReplace, storyListFragment).commit()
                        }
                        fragmentManager.beginTransaction().hide(thisFragment)
                                .show(storyListFragment).commit()
                        thisFragment = storyListFragment
                    }
                    1 -> {
                        if (!scenicSpotFragment.isAdded()) {
                            fragmentManager.beginTransaction().add(R.id.sectionFragmentReplace, scenicSpotFragment).commit()
                        }
                        fragmentManager.beginTransaction().hide(thisFragment)
                                .show(scenicSpotFragment)
                                .commit()
                        thisFragment = scenicSpotFragment
                    }
                    0 -> {
                        if (!exploreFragment.isAdded()) {
                            fragmentManager.beginTransaction().add(R.id.sectionFragmentReplace, exploreFragment).commit()
                        }
                        fragmentManager.beginTransaction().hide(thisFragment)
                                .show(exploreFragment).commit()
                        thisFragment = exploreFragment
                    }
                }

                tab.view.tvSection.setTextColor(Color.parseColor("#FF6666"))

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.view.tvSection.setTextColor(Color.parseColor("#6C6C6C"))
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })


    }
}
