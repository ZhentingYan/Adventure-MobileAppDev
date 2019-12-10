package com.tongjisse.adventure.utils

import android.content.Context
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.style.cityjd.JDCityConfig
import com.lljjcoder.style.cityjd.JDCityPicker

class CityPickerHelper(context: Context?, listener: OnCityItemClickListener) {
    internal lateinit var cityPicker: JDCityPicker
    var mWheelType: JDCityConfig.ShowType = JDCityConfig.ShowType.PRO_CITY_DIS
    private val jdCityConfig = JDCityConfig.Builder().build()

    /**
     * 构造函数 CityPikerHelper
     * @param context:Context 必须传入Activity/Fragment的上下文
     * @param listener:OnCityItemListener 点击事件监听器
     * @author ZhentingYan
     */
    init {
        jdCityConfig.showType = mWheelType
        cityPicker = JDCityPicker()
        //初始化数据
        cityPicker.init(context)
        //设置JD选择器样式位只显示省份、城市、区县三级
        cityPicker.setConfig(jdCityConfig)
        //设置监听器
        cityPicker.setOnCityItemClickListener(listener)
    }

    /**
     * 展示JDCityStyle的CityPicker
     * @author ZhentingYan
     */
    fun showJD() {
        cityPicker.showCityPicker()
    }
}