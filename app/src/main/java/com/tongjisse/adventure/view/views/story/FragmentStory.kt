package com.tongjisse.adventure.view.views.ScenicSpot

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lljjcoder.style.cityjd.JDCityConfig
import com.lljjcoder.style.cityjd.JDCityPicker
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.network.ScenicSpotRepository
import com.tongjisse.adventure.model.ScenicSpotGallery
import com.tongjisse.adventure.presenter.ScenicSpotPresenter
import com.tongjisse.adventure.utils.SensorManagerHelper
import com.tongjisse.adventure.view.common.BaseFragmentWithPresenter
import com.tongjisse.adventure.view.main.MainListAdapter
import com.tongjisse.adventure.view.main.ScenicSpotView
import kotlinx.android.synthetic.main.fragment_scenicspot.*
import com.tongjisse.adventure.view.main.ScenicSpotGalleryAdapter
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.common.bindToSwipeRefresh
import kotlinx.android.synthetic.main.fragment_password.*
import kotlinx.android.synthetic.main.fragment_scenicspot.tvError
import java.util.*

class FragmentStory :BaseFragmentWithPresenter(),ScenicSpotView{
    internal lateinit var cityPicker: JDCityPicker
    var mWheelType: JDCityConfig.ShowType = JDCityConfig.ShowType.PRO_CITY_DIS
    private val jdCityConfig = JDCityConfig.Builder().build()
    private lateinit var userSP: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    companion object {
        var locationClient: AMapLocationClient? = null
        var locationOption: AMapLocationClientOption? = null
        var shakeJudge:Boolean=false
    }
    override var refresh by bindToSwipeRefresh(R.id.swipeRefreshView)
    override val presenter by lazy{ ScenicSpotPresenter(this,ScenicSpotRepository.get())}

    override fun show(items: List<ScenicSpotGallery>) {
        if(shakeJudge){
            shakeJudge=false
            if(!items.isEmpty()){
                val intent = Intent(context, ScenicSpotDetailActivity::class.java)
                var recommendedSpot= items[getRandom(0,items.size).toInt()]
                intent.putExtra("id", recommendedSpot.id);   //键值对
                intent.putExtra("picAddr", recommendedSpot.imageurl);   //键值对
                context!!.startActivity(intent)
            }
        }else {
            if (!items.isEmpty()) {
                ErrorLayout.visibility = View.GONE
                val categoryItemAdapters = items.map(::ScenicSpotGalleryAdapter)
                recyclerView.adapter = MainListAdapter(categoryItemAdapters)
            } else {
                ErrorLayout.visibility = View.VISIBLE
                tvError.text = "在" + userSP.getString("DISTRICT_NAME", " ") + "似乎没有景点......"
            }
        }

    }
    override fun showError(error: Throwable) {
        if(shakeJudge){
            context!!.toast("看来没有和你有缘的景点呢～")
        } else {
            ErrorLayout.visibility = View.VISIBLE
            tvError.text = "在" + userSP.getString("DISTRICT_NAME", " ") + "似乎没有景点......"
            context!!.toast("Error:${error.message}")
            error.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_scenicspot, container, false)
        initLocation()
        initLocation()
        userSP = context!!.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        editor = userSP.edit();
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager= GridLayoutManager(context,2)
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
                presenter.loadScenicSpots(userSP.getString("DISTRICT_NAME",""))

            }

            override fun onCancel() {}
        })
        swipeRefreshView.setOnRefreshListener {
            swipeRefreshView.isRefreshing=true
            locationClient!!.startLocation()
        }
        tvLocation.setOnClickListener {
            showJD()
        }
        ivIcon.setOnClickListener {
            locationClient!!.startLocation()
        }
        tvSearchAnywhere.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(userSP.getString("DISTRICT_NAME","").equals("")){
                    ErrorLayout.visibility=View.VISIBLE
                    tvError.text="请选择你的目的地呀......"
                }
                presenter.loadScenicSpotsWithKeywords(userSP.getString("DISTRICT_NAME",""),tvSearchAnywhere.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        var sensorHelper = SensorManagerHelper(context)
        sensorHelper.setOnShakeListener(object : SensorManagerHelper.OnShakeListener {
            override fun onShake() {
                shakeJudge=true
                presenter.loadScenicSpots(userSP.getString("DISTRICT_NAME",""))
            }
        })
        //presenter.onViewCreated()
        locationClient!!.startLocation()//定位一次
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
                presenter.loadScenicSpots(location.district)
            } else {
                //定位失败
                Toast.makeText(activity, "定位失败，请检查GPS定位是否打开", Toast.LENGTH_LONG).show()
                errorLayout.visibility=View.VISIBLE
                tvError.text="定位失败，请检查GPS定位是否打开..."
                Log.e("Locate",location.errorCode.toString()+" "+location.errorInfo)
            }

        } else {
            Toast.makeText(activity, "定位失败，请检查GPS定位是否打开", Toast.LENGTH_LONG).show()
            errorLayout.visibility=View.VISIBLE
            tvError.text="定位失败，请检查GPS定位是否打开..."
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

    fun getRandom(min: Int, max: Int): String {
        val random = Random()
        val s = random.nextInt(max) % (max - min + 1) + min
        return s.toString()

    }
}