package com.tongjisse.adventure.view.views.Explore

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.weather.LocalWeatherForecastResult
import com.tongjisse.adventure.R
import com.amap.api.services.weather.WeatherSearch
import com.amap.api.services.weather.WeatherSearchQuery
import com.amap.api.services.weather.LocalWeatherLiveResult
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lljjcoder.style.cityjd.JDCityConfig
import com.lljjcoder.style.cityjd.JDCityPicker
import com.tongjisse.adventure.dao.WishListDao
import com.tongjisse.adventure.view.main.MainListAdapter
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.fragment_explore.tvLocation
import com.tongjisse.adventure.view.main.MainWishListAdapter
import com.tongjisse.adventure.view.views.Main.MenuActivity

class ExploreFragment:Fragment(),WeatherSearch.OnWeatherSearchListener{
    private lateinit var userSP: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var mquery:WeatherSearchQuery
    private lateinit var mweathersearch:WeatherSearch
    private val wishListDao=WishListDao()
    var locationClient: AMapLocationClient? = null
    var locationOption: AMapLocationClientOption? = null
    internal lateinit var cityPicker: JDCityPicker
    var mWheelType: JDCityConfig.ShowType = JDCityConfig.ShowType.PRO_CITY_DIS
    private val jdCityConfig = JDCityConfig.Builder().build()
    val SUN = "晴"
    val CLOUDY = "阴"
    val SNOW = "雪"
    val RAIN = "雨"
    val SUN_CLOUD = "多云"
    val THUNDER = "雷"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_explore, container, false)
        initLocation()
        userSP = context!!.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        editor = userSP.edit();
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainRecyclerView.layoutManager= GridLayoutManager(context,1)
        super.onViewCreated(view, savedInstanceState)
        if(userSP.getString("DISTRICT_NAME","").equals(""))
            locationClient!!.startLocation()//定位一次
        else {
            tvLikes.text=userSP.getString("DISTRICT_NAME","")+"的心愿单"
            loadWeather(userSP.getString("DISTRICT_NAME",""))
            tvLocation.text=userSP.getString("PROVINCE_NAME","")+" "+userSP.getString("CITY_NAME","")+" "+userSP.getString("DISTRICT_NAME","")
        }
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
                loadWeather(district!!.name)
                showUserWishLists()
                tvLikes.text=district!!.name+"的心愿单"
            }

            override fun onCancel() {}
        })
        tvLocation.setOnClickListener {
            showJD()
        }
        ivIcon.setOnClickListener {
            tvTemperature.text="暂无温度"
            tvWeather.text="正在获取天气......"
            locationClient!!.startLocation()

        }
        sceneLayout.setOnClickListener{
            MenuActivity.sectionTab.getTabAt(1)!!.select();
        }
        storyLayout.setOnClickListener{
            MenuActivity.sectionTab.getTabAt(2)!!.select();
        }
        mainRecyclerView.setOnClickListener {
            MenuActivity.sectionTab.getTabAt(3)!!.select();
        }
        showUserWishLists()

    }

    fun loadWeather(city:String){
        mquery = WeatherSearchQuery(userSP.getString("DISTRICT_NAME",""), WeatherSearchQuery.WEATHER_TYPE_LIVE)
        mweathersearch = WeatherSearch(context)
        mweathersearch.setOnWeatherSearchListener(this)
        mweathersearch.setQuery(mquery)
        mweathersearch.searchWeatherAsyn() //异步搜索
    }

    override fun onWeatherForecastSearched(p0: LocalWeatherForecastResult?, p1: Int) {
        //未使用，不作实现
    }

    override fun onWeatherLiveSearched(weatherLiveResult: LocalWeatherLiveResult?, rCode: Int) {
        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.liveResult != null) {
                var weatherlive = weatherLiveResult.liveResult
                tvTemperature.text=weatherlive.temperature+"℃"
                tvWeather.text=weatherlive.weather
                ivWeather.setImageResource(getWeatherResId(weatherlive.weather))

            } else {
                //无结果显示
                tvTemperature.text="暂无温度"
                tvWeather.text="天气获取失败"
                ivWeather.setImageResource(getWeatherResId("未知天气"))
            }
        } else {
            //这里错误
            tvTemperature.text="暂无温度"
            tvWeather.text="天气获取失败"
            ivWeather.setImageResource(getWeatherResId("未知天气"))
        }
    }

    fun getWeatherResId(weather:String):Int{
        if(weather.contains(SNOW))
            return R.drawable.snow
        if(weather.contains(CLOUDY))
            return R.drawable.cloudy
        if(weather.contains(SUN))
            return R.drawable.sun
        if(weather.contains(RAIN))
            return R.drawable.rain
        if(weather.contains(THUNDER))
            return R.drawable.thunder
        if(weather.contains(SUN_CLOUD))
            return R.drawable.sun_cloud
        return R.drawable.cloudy
    }
    private fun showJD() {
        cityPicker.showCityPicker()
    }

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

    internal var locationListener: AMapLocationListener = AMapLocationListener { location ->
        if (null != location) {

            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.errorCode == 0) {
                tvLocation.text=location.province+" "+location.city+" "+location.district
                Toast.makeText(activity, "定位成功，您目前在"+location.province+location.city+location.district, Toast.LENGTH_LONG).show()
                editor.putString("DISTRICT_NAME",location.district)
                editor.putString("PROVINCE_NAME",location.province)
                editor.putString("CITY_NAME",location.city)
                editor.putString("LATITUDE",location.latitude.toString())
                editor.putString("LONGTITUDE",location.longitude.toString())
                editor.commit()
                loadWeather(location.district)
                showUserWishLists()
                tvLikes.text=location.district+"的心愿单"
            } else {
                //定位失败
                Toast.makeText(activity, "定位失败，请检查GPS定位是否打开", Toast.LENGTH_LONG).show()
                Log.e("Locate",location.errorCode.toString()+" "+location.errorInfo)
                loadWeather(" ")
            }

        } else {
            Toast.makeText(activity, "定位失败，请检查GPS定位是否打开", Toast.LENGTH_LONG).show()
            loadWeather(" ")
        }
        locationClient!!.stopLocation()
    }

    private fun initLocation() {
        //初始化client
        locationClient = AMapLocationClient(context)
        locationOption = getDefaultOption()
        //设置定位参数
        locationClient!!.setLocationOption(locationOption)
        // 设置定位监听
        locationClient!!.setLocationListener(locationListener)
    }

    fun showUserWishLists(){
        val user=userSP.getString("EMAIL","")
        val district=userSP.getString("DISTRICT_NAME","")
        var userWishList=wishListDao.queryByUserAndDistrict(user,district)
        //筛选不符合用户所在地的心愿列表
        if(userWishList!=null) {

            if (userWishList.size != 0) {
                mainErrorLayout.visibility = View.GONE
                mainRecyclerView.visibility=View.VISIBLE
                svWishList.visibility=View.VISIBLE
                val categoryItemAdapters = userWishList.map(::MainWishListAdapter)
                mainRecyclerView.adapter = MainListAdapter(categoryItemAdapters)
            } else {
                mainErrorLayout.visibility = View.VISIBLE
                mainRecyclerView.visibility=View.GONE
                svWishList.visibility=View.GONE

                tvError.text = "在" + userSP.getString("DISTRICT_NAME", " ") + "你还没有心愿哦......"
            }
        }else{
            mainErrorLayout.visibility = View.VISIBLE
            mainRecyclerView.visibility=View.GONE
            svWishList.visibility=View.GONE
            tvError.text = "在" + userSP.getString("DISTRICT_NAME", " ") + "你还没有心愿哦......"
        }
    }
}
