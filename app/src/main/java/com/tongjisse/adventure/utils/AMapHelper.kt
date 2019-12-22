package com.tongjisse.adventure.utils

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng

class AMapHelper(context: Context?, locationListener: AMapLocationListener) {
    var locationClient: AMapLocationClient? = null
    var locationOption: AMapLocationClientOption? = null
    var context: Context?
    var locationListener: AMapLocationListener

    /**
     * AMapHelper Constructor
     * @param context：Context 必须传入Context环境
     * @param locationListener：AMapLocationListener 必须传入定位返回监听器
     * @author ZhentingYan
     */

    init {
        this.context = context
        this.locationListener = locationListener
        //初始化定位设置
        initLocation()
    }

    /**
     * Initialize Location Parameters& Location Client
     *
     * @author ZhentingYan
     */
    private fun initLocation() {
        //初始化client
        locationClient = AMapLocationClient(context)
        locationOption = getDefaultOption()
        //设置默认定位参数
        locationClient!!.setLocationOption(locationOption)
        // 设置定位监听器
        locationClient!!.setLocationListener(locationListener)
    }

    /**
     * Called by initLoaction(), which is used for getting default options
     *
     * @author ZhentingYan
     * @return 默认的高德地图定位客户端参数设置mOption:AMapLocationClientOption
     */

    private fun getDefaultOption(): AMapLocationClientOption {
        val mOption = AMapLocationClientOption()
        mOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.isGpsFirst = false//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.httpTimeOut = 30000//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.interval = 2000//可选，设置定位间隔。默认为2秒
        mOption.isNeedAddress = true//可选，设置是否返回逆地理地址信息。默认是true
        mOption.isOnceLocation = false//可选，设置是否单次定位。默认是false
        mOption.isOnceLocationLatest = false//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP)//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.isSensorEnable = false//可选，设置是否使用传感器。默认是false
        mOption.isWifiScan = true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.isLocationCacheEnable = true //可选，设置是否使用缓存定位，默认为true
        mOption.geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption
    }

    /**
     * 计算用户与景点距离
     * @param user_latitude 用户纬度
     * @param user_longitude 用户经度
     * @param latitude:String 景点纬度
     * @param longitude:String 景点经度
     */
    fun getDistance(user_latitude: String, user_longitude: String, latitude: String, longitude: String): Float {
        if (user_latitude.equals("") || user_longitude.equals("")) {
            return -0.1 as Float
        } else {
            val userPoint = LatLng(user_latitude.toDouble(), user_longitude.toDouble())
            val placePoint = LatLng(latitude.toDouble(), longitude.toDouble())
            val distance = AMapUtils.calculateLineDistance(userPoint, placePoint)
            return distance
        }
    }
}