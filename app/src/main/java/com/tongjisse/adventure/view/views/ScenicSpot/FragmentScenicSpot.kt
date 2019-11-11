package com.tongjisse.adventure.view.views.ScenicSpot

import android.os.Bundle
import android.support.v4.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_scenicspot.*


class FragmentScenicSpot :Fragment(){
    internal lateinit var cityPicker: JDCityPicker
    var mWheelType: JDCityConfig.ShowType = JDCityConfig.ShowType.PRO_CITY
    private val jdCityConfig = JDCityConfig.Builder().build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_scenicspot, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        jdCityConfig.showType = mWheelType
        cityPicker = JDCityPicker()
        //初始化数据
        cityPicker.init(context)
        //设置JD选择器样式位只显示省份和城市两级
        cityPicker.setConfig(jdCityConfig)
        cityPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
            override fun onSelected(province: ProvinceBean?, city: CityBean?, district: DistrictBean?) {

                var proData: String? = null
                if (province != null) {
                    proData = "name:  " + province.name + "   id:  " + province.id
                }

                var cituData: String? = null
                if (city != null) {
                    tvLocation.text = city.name
                }


                var districtData: String? = null
                if (district != null) {
                    districtData = "name:  " + district.name + "   id:  " + district.id
                }

            }

            override fun onCancel() {}
        })
        tvLocation.setOnClickListener {
            showJD()
        }
    }
    private fun showJD() {
        cityPicker.showCityPicker()
    }

}