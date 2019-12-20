package com.tongjisse.adventure.view.views.ScenicSpot

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.amap.api.location.AMapLocationListener
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.network.ScenicSpotRepository
import com.tongjisse.adventure.model.ScenicSpotGallery
import com.tongjisse.adventure.presenter.ScenicSpot.ScenicSpotPresenter
import com.tongjisse.adventure.utils.*
import com.tongjisse.adventure.view.common.BaseFragmentWithPresenter
import com.tongjisse.adventure.view.common.bindToSwipeRefresh
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.MainListAdapter
import com.tongjisse.adventure.view.main.ScenicSpot.ScenicSpotGalleryAdapter
import com.tongjisse.adventure.view.main.ScenicSpot.ScenicSpotView
import kotlinx.android.synthetic.main.fragment_scenicspot.*

class ScenicSpotFragment : BaseFragmentWithPresenter(), ScenicSpotView {
    /**
     * Final Strings
     */
    val LOCATE_FAILURE_INFO = "定位失败，请检查GPS定位是否开启..."
    val NO_DISTRICT_SELECTED_INFO = "请选择你的目的地呀......"
    /**
     * Multiple Helpers
     */
    lateinit var mJDCityPicker: CityPickerHelper
    lateinit var mAMapHelper: AMapHelper
    lateinit var mSessionManager: SessionManager
    lateinit var mSensorManagerHelper: SensorManagerHelper
    /**
     * Delegate
     */
    override var refresh by bindToSwipeRefresh(R.id.swipeRefreshView)
    override val presenter by lazy { ScenicSpotPresenter(this, ScenicSpotRepository.get()) }

    companion object {
        //Used for ScenicSpotDetailActivity
        var shakeJudge: Boolean = false
    }

    //AmapLocationListener Implementation
    internal var mLocationListener: AMapLocationListener = AMapLocationListener { location ->
        if (null != location) {
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.errorCode == 0) {
                mSessionManager.refineLocation(location.province, location.city, location.district, location.longitude.toString(), location.latitude.toString())
                tvLocation.text = mSessionManager.defaultAddress
                context!!.toast("定位成功，您目前在${mSessionManager.defaultAddress}")
                presenter.loadScenicSpots(location.district)
            } else {
                //定位失败时显示错误信息
                showLocateError()
                Log.e("Locate", location.errorCode.toString() + " " + location.errorInfo)
            }

        } else {
            showLocateError()
        }
        mAMapHelper.locationClient!!.stopLocation()
    }

    /**
     * Call this function when meeting search errors
     * Notify views to change
     * @author ZhentingYan
     */
    fun showSearchError() {
        ErrorLayout.visibility = View.VISIBLE
        tvError.text = "在${mSessionManager.district}似乎没有景点......"
    }

    /**
     * Call this function when meeting locating erros
     * Notify views to change
     * @author ZhentingYan
     */

    fun showLocateError() {
        Toast.makeText(activity, LOCATE_FAILURE_INFO, Toast.LENGTH_LONG).show()
        ErrorLayout.visibility = View.VISIBLE
        tvError.text = LOCATE_FAILURE_INFO
    }

    /**
     * Override fun showError defined in interface
     * @param error:Throwable
     * @author ZhentingYan
     */
    override fun showError(error: Throwable) {
        if (shakeJudge) {
            context!!.toast("看来没有和你有缘的景点呢～")
        } else {
            showSearchError()
            context!!.toast("Error:${error.message}")
            error.printStackTrace()
        }
        shakeJudge = false
    }

    /**
     * Override fun show defined in interface
     * @param items:List<ScenicSpotGallery>
     * @author ZhentingYan
     */
    override fun show(items: List<ScenicSpotGallery>) {
        if (shakeJudge) {
            //判断本次show是否是摇一摇产生的
            if (!items.isEmpty()) {
                var recommendedSpot = items[MathUtils.getRandom(0, items.size).toInt()]
                val intent = Intent(context, ScenicSpotDetailActivity::class.java)
                intent.putExtra("id", recommendedSpot.id);
                intent.putExtra("picAddr", recommendedSpot.imageurl);
                context!!.startActivity(intent)
            }
        } else {
            //本次show不是摇一摇产生的结果
            if (!items.isEmpty()) {
                ErrorLayout.visibility = View.GONE
                val categoryItemAdapters = items.map(::ScenicSpotGalleryAdapter)

                recyclerView.adapter = MainListAdapter(categoryItemAdapters)
            } else {
                showSearchError()
            }
        }
    }

    /**
     * Override onCreateView()
     * @author ZhentingYan
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_scenicspot, container, false)
        mSessionManager = SessionManager(context)
        mSensorManagerHelper = SensorManagerHelper(context)
        return view
    }

    /**
     * Override OnViewCreated()
     * @author ZhentingYan
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        mAMapHelper = AMapHelper(context, mLocationListener)
        mJDCityPicker = CityPickerHelper(context, object : OnCityItemClickListener() {
            override fun onSelected(province: ProvinceBean?, city: CityBean?, district: DistrictBean?) {
                mSessionManager.refineLocation(province!!.name, city!!.name, district!!.name, mSessionManager.longitude, mSessionManager.latitude)
                tvLocation.text = mSessionManager.defaultAddress
                presenter.loadScenicSpots(mSessionManager.district)

            }
        })
        swipeRefreshView.setOnRefreshListener {
            swipeRefreshView.isRefreshing = true
            presenter.loadScenicSpots(mSessionManager.district)
        }
        tvLocation.setOnClickListener {
            mJDCityPicker.showJD()
        }
        ivIcon.setOnClickListener {
            mAMapHelper.locationClient!!.startLocation()
        }
        tvSearchAnywhere.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (mSessionManager.district.equals("")) {
                    ErrorLayout.visibility = View.VISIBLE
                    tvError.text = NO_DISTRICT_SELECTED_INFO
                }
                presenter.loadScenicSpotsWithKeywords(mSessionManager.district, tvSearchAnywhere.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Not need to override
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Not need to override
            }
        })
        mSensorManagerHelper.setOnShakeListener(object : SensorManagerHelper.OnShakeListener {
            override fun onShake() {
                shakeJudge = true
                presenter.loadScenicSpots(mSessionManager.district)
            }
        })
    }

    fun fragmentInit() {
        if (mSessionManager.district.equals("")) {
            mAMapHelper.locationClient!!.startLocation()//定位一次
        } else {
            tvLocation.text = mSessionManager.defaultAddress
            presenter.loadScenicSpots(mSessionManager.district)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden)
            fragmentInit()
    }
}