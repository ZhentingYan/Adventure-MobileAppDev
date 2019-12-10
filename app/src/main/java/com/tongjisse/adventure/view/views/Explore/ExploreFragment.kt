package com.tongjisse.adventure.view.views.Explore

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.weather.LocalWeatherForecastResult
import com.amap.api.services.weather.LocalWeatherLiveResult
import com.amap.api.services.weather.WeatherSearch
import com.amap.api.services.weather.WeatherSearchQuery
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.WishList
import com.tongjisse.adventure.presenter.Explore.ExplorePresenter
import com.tongjisse.adventure.utils.AMapHelper
import com.tongjisse.adventure.utils.CityPickerHelper
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.BaseFragmentWithPresenter
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.Explore.ExploreView
import com.tongjisse.adventure.view.main.Explore.MainWishListAdapter
import com.tongjisse.adventure.view.main.MainListAdapter
import com.tongjisse.adventure.view.views.Main.MenuActivity
import kotlinx.android.synthetic.main.fragment_explore.*
import java.sql.SQLException

class ExploreFragment : BaseFragmentWithPresenter(), WeatherSearch.OnWeatherSearchListener, ExploreView {
    private lateinit var mquery: WeatherSearchQuery
    private lateinit var mweathersearch: WeatherSearch
    private lateinit var mAMapHelper: AMapHelper
    private lateinit var mSessionManager: SessionManager
    private lateinit var mJDCityPiker: CityPickerHelper
    override val presenter by lazy { ExplorePresenter(this) }
    val SUN = "晴"
    val CLOUDY = "阴"
    val SNOW = "雪"
    val RAIN = "雨"
    val SUN_CLOUD = "多云"
    val THUNDER = "雷"
    val LOCATE_FAILURE_INFO = "定位失败，请检查GPS定位是否开启..."


    //AmapLocationListener Implementation
    internal var mLocationListener: AMapLocationListener = AMapLocationListener { location ->
        if (null != location) {
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.errorCode == 0) {
                mSessionManager.refineLocation(location.province, location.city, location.district, location.longitude.toString(), location.latitude.toString())
                tvLocation.text = mSessionManager.defaultAddress
                context!!.toast("定位成功，您目前在${mSessionManager.defaultAddress}")
                loadWeather(location.district)
                presenter.showUserWishLists(mSessionManager.email, mSessionManager.district)
                tvLikes.text = location.district + "的心愿单"
            } else {
                //定位失败
                context!!.toast(LOCATE_FAILURE_INFO)
                loadWeather(" ")
                presenter.showUserWishLists(mSessionManager.email, " ")
            }

        } else {
            context!!.toast(LOCATE_FAILURE_INFO)
            loadWeather(" ")
            presenter.showUserWishLists(mSessionManager.email, " ")
        }
        mAMapHelper.locationClient!!.stopLocation()
    }

    /**
     * Implements the View interface
     * @author ZhentingYan
     */
    override fun getUserWishListsSuccess(userWishList: List<WishList>) {
        //筛选不符合用户所在地的心愿列表
        if (userWishList != null) {
            if (userWishList.size != 0) {
                //展示用户当地心愿单
                mainErrorLayout.visibility = View.GONE
                mainRecyclerView.visibility = View.VISIBLE
                svWishList.visibility = View.VISIBLE
                val categoryItemAdapters = userWishList.map(::MainWishListAdapter)
                mainRecyclerView.adapter = MainListAdapter(categoryItemAdapters)
            } else {
                mainErrorLayout.visibility = View.VISIBLE
                mainRecyclerView.visibility = View.GONE
                svWishList.visibility = View.GONE
                tvError.text = "在${mSessionManager.district}你还没有心愿哦......"
            }
        } else {
            mainErrorLayout.visibility = View.VISIBLE
            mainRecyclerView.visibility = View.GONE
            svWishList.visibility = View.GONE
            tvError.text = "在${mSessionManager.district}你还没有心愿哦......"
        }
    }

    override fun getUserWishListsFailed(error: SQLException) {
        mainErrorLayout.visibility = View.VISIBLE
        mainRecyclerView.visibility = View.GONE
        svWishList.visibility = View.GONE
        tvError.text = "获取心愿单异常......"
        context!!.toast("获取心愿单异常，错误信息:${error.message}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_explore, container, false)
        mSessionManager = SessionManager(context)
        mAMapHelper = AMapHelper(context, mLocationListener)
        return view
    }

    /**
     * 抽象出Fragment初始化的操作，这样在页面之间切换时可以保持location的一致性
     * @author ZhentingYan
     */
    fun fragmentInit() {
        if (mSessionManager.district.equals(""))
            mAMapHelper.locationClient!!.startLocation()//定位一次
        else {
            tvLikes.text = "${mSessionManager.district}的心愿单"
            loadWeather(mSessionManager.district)
            tvLocation.text = mSessionManager.defaultAddress
            presenter.showUserWishLists(mSessionManager.email, mSessionManager.district)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mJDCityPiker = CityPickerHelper(context, object : OnCityItemClickListener() {
            override fun onSelected(province: ProvinceBean?, city: CityBean?, district: DistrictBean?) {
                mSessionManager.refineLocation(province!!.name, city!!.name, district!!.name, mSessionManager.longitude, mSessionManager.latitude)
                tvLocation.text = mSessionManager.defaultAddress
                loadWeather(district!!.name)
                presenter.showUserWishLists(mSessionManager.email, mSessionManager.district)
                tvLikes.text = district!!.name + "的心愿单"
            }

            override fun onCancel() {}
        })
        //加载天气、定位、心愿单信息
        fragmentInit()
        tvLocation.setOnClickListener {
            mJDCityPiker.showJD()
        }
        ivIcon.setOnClickListener {
            tvTemperature.text = "暂无温度"
            tvWeather.text = "正在获取天气......"
            mAMapHelper.locationClient!!.startLocation()
        }
        sceneLayout.setOnClickListener {
            MenuActivity.sectionTab.getTabAt(1)!!.select();
        }
        storyLayout.setOnClickListener {
            MenuActivity.sectionTab.getTabAt(2)!!.select();
        }
        mainRecyclerView.setOnClickListener {
            MenuActivity.sectionTab.getTabAt(3)!!.select();
        }
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden)
            fragmentInit()
    }
    /**
     * Load Weather with city or district name
     * @param city:String
     * @author ZhentingYan
     */
    fun loadWeather(city: String) {
        mquery = WeatherSearchQuery(mSessionManager.district, WeatherSearchQuery.WEATHER_TYPE_LIVE)
        mweathersearch = WeatherSearch(context)
        mweathersearch.setOnWeatherSearchListener(this)
        mweathersearch.setQuery(mquery)
        mweathersearch.searchWeatherAsyn() //异步搜索
    }

    override fun onWeatherForecastSearched(p0: LocalWeatherForecastResult?, p1: Int) {
        //未使用，不作实现
    }

    /**
     * Implements onWeatherLiveSearched
     * @author ZhentingYan
     */
    override fun onWeatherLiveSearched(weatherLiveResult: LocalWeatherLiveResult?, rCode: Int) {
        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.liveResult != null) {
                var weatherlive = weatherLiveResult.liveResult
                tvTemperature.text = weatherlive.temperature + "℃"
                tvWeather.text = weatherlive.weather
                ivWeather.setImageResource(getWeatherResId(weatherlive.weather))

            } else {
                //无结果显示
                tvTemperature.text = "暂无温度"
                tvWeather.text = "天气获取失败"
                ivWeather.setImageResource(getWeatherResId("未知天气"))
            }
        } else {
            //这里错误
            tvTemperature.text = "暂无温度"
            tvWeather.text = "天气获取失败"
            ivWeather.setImageResource(getWeatherResId("未知天气"))
        }
    }

    /**
     * getWeatherResId according to the weather text
     * @param weather:String
     * @author ZhentingYan
     */
    fun getWeatherResId(weather: String): Int {
        if (weather.contains(SNOW))
            return R.drawable.snow
        if (weather.contains(CLOUDY))
            return R.drawable.cloudy
        if (weather.contains(SUN))
            return R.drawable.sun
        if (weather.contains(RAIN))
            return R.drawable.rain
        if (weather.contains(THUNDER))
            return R.drawable.thunder
        if (weather.contains(SUN_CLOUD))
            return R.drawable.sun_cloud
        return R.drawable.cloudy
    }

}
