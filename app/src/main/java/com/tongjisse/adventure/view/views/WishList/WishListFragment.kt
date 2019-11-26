package com.tongjisse.adventure.view.views.WishList

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lljjcoder.style.cityjd.JDCityConfig
import com.lljjcoder.style.cityjd.JDCityPicker
import com.tongjisse.adventure.R
import com.tongjisse.adventure.dao.WishListDao
import com.tongjisse.adventure.view.main.MainListAdapter
import kotlinx.android.synthetic.main.fragment_wishlist.*
import com.tongjisse.adventure.view.main.WishListAdapter


class WishListFragment :Fragment() {
    internal lateinit var cityPicker: JDCityPicker
    var mWheelType: JDCityConfig.ShowType = JDCityConfig.ShowType.PRO_CITY_DIS
    private val jdCityConfig = JDCityConfig.Builder().build()
    private var wishListDao= WishListDao()

    private lateinit var userSP: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)
        userSP = context!!.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        editor = userSP.edit();
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        wishRecyclerView.layoutManager= GridLayoutManager(context,1)
        jdCityConfig.showType = mWheelType
        cityPicker = JDCityPicker()
        //初始化数据
        cityPicker.init(context)
        //设置JD选择器样式位只显示省份、城市、区县三级
        cityPicker.setConfig(jdCityConfig)
        cityPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
            override fun onSelected(province: ProvinceBean?, city: CityBean?, district: DistrictBean?) {

                if (province != null) {
                    tvLocation.text=province.name
                    editor.putString("PROVINCE_NAME", province.name)
                }

                if (city != null) {
                    tvLocation.text=tvLocation.text.toString()+" "+city.name
                    editor.putString("CITY_NAME", city.name)

                }

                if (district != null) {
                    tvLocation.text=tvLocation.text.toString()+" "+district.name
                    editor.putString("DISTRICT_NAME", district.name)
                }
                editor.commit()
                //presenter.loadScenicSpots(userSP.getString("DISTRICT_NAME",""))
                showUserWishLists()

            }

            override fun onCancel() {}
        })
        swipeRefreshView.setOnRefreshListener {
            swipeRefreshView.isRefreshing=true
            //ScenicSpotFragment.locationClient!!.startLocation()
            showUserWishLists()
            swipeRefreshView.isRefreshing=false
        }
        if(userSP.getString("DISTRICT_NAME","").equals("")){
            //locationClient!!.startLocation()//定位一次
        }else{
            tvLocation.text=userSP.getString("PROVINCE_NAME","")+" "+userSP.getString("CITY_NAME","")+" "+userSP.getString("DISTRICT_NAME","")
        }
        tvLocation.setOnClickListener {
            cityPicker.showCityPicker()
        }
        showUserWishLists()
    }
    fun showUserWishLists(){
        val user=userSP.getString("EMAIL","")
        val district=userSP.getString("DISTRICT_NAME","")
        var userWishList=wishListDao.queryByUserAndDistrict(user,district)
        //筛选不符合用户所在地的心愿列表
        if(userWishList!=null) {

            if (userWishList.size != 0) {
                ErrorLayout.visibility = View.GONE
                wishRecyclerView.visibility=View.VISIBLE
                val categoryItemAdapters = userWishList.map(::WishListAdapter)
                wishRecyclerView.adapter = MainListAdapter(categoryItemAdapters)
            } else {
                ErrorLayout.visibility = View.VISIBLE
                wishRecyclerView.visibility=View.GONE
                tvError.text = "在" + userSP.getString("DISTRICT_NAME", " ") + "你还没有心愿哦......"
            }
        }else{
            ErrorLayout.visibility = View.VISIBLE
            wishRecyclerView.visibility=View.GONE
            tvError.text = "在" + userSP.getString("DISTRICT_NAME", " ") + "你还没有心愿哦......"
        }
    }

}