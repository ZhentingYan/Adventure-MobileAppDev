package com.tongjisse.adventure.view.views.ScenicSpot

import android.os.Bundle
import android.view.Window
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.WishList
import com.tongjisse.adventure.data.network.ScenicSpotRepository
import com.tongjisse.adventure.model.ScenicSpotDetail
import com.tongjisse.adventure.presenter.ScenicSpot.ScenicSpotDetailPresenter
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.BaseActivityWithPresenter
import com.tongjisse.adventure.view.common.loadImage
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.ScenicSpot.ScenicSpotDetailView
<<<<<<< HEAD
import kotlinx.android.synthetic.main.fragment_scenicspot_desc.*
=======
import kotlinx.android.synthetic.main.activity_story_detail.*
import kotlinx.android.synthetic.main.fragment_scenicspot_desc.*
import kotlinx.android.synthetic.main.fragment_scenicspot_desc.tvPlaceTitle
import kotlinx.android.synthetic.main.fragment_scenicspot_desc.tvPublisher
>>>>>>> 07c9524f5977b3d98e481c08f7ea7e5c9196886f
import java.sql.SQLException
import java.text.DecimalFormat


class ScenicSpotDetailActivity : BaseActivityWithPresenter(), ScenicSpotDetailView {
    private lateinit var mMap: AMap
    private lateinit var myLocationStyle: MyLocationStyle
    private lateinit var mSessionManager: SessionManager
    //判断当前POI是否在用户的心愿单内
    var inList = false
    //poiID
    private lateinit var poiID: String
    //心愿详情
    private lateinit var detailWish: WishList
    private lateinit var imageUrl: String
    private lateinit var distance: String
    override val presenter by lazy { ScenicSpotDetailPresenter(this, ScenicSpotRepository.get()) }
    /**
     * Called by presenter when ScenicSpotDetials are got successfully
     * @param items:List<ScenicSpotDetail>
     * @author ZhentingYan
<<<<<<< HEAD
=======
     *
     * Comment: Fix error caused by parameter name's conflict
     * @Modified: FeifanWang
>>>>>>> 07c9524f5977b3d98e481c08f7ea7e5c9196886f
     */
    override fun show(items: List<ScenicSpotDetail>) {
        val detail = items[0]
        val marker = mMap.addMarker(MarkerOptions().position(LatLng(detail.latitude.toDouble(), detail.longitude.toDouble())).title(detail.name).snippet(detail.address))
        tvAddress.text = detail.address
        tvPublisher.text = detail.intro
        tvType.text = detail.type
        ivWish.loadImage(imageUrl)
<<<<<<< HEAD
        tvStoryTitle.text = detail.name
=======
        tvPlaceTitle.text = detail.name
>>>>>>> 07c9524f5977b3d98e481c08f7ea7e5c9196886f
        if (mSessionManager.latitude.equals("") || mSessionManager.longitude.equals("")) {
            tvDistance.text = "获取距离失败..."
        } else {
            val userPoint = LatLng(mSessionManager.latitude.toDouble(), mSessionManager.longitude.toDouble())
            val placePoint = LatLng(detail.latitude.toDouble(), detail.longitude.toDouble())
            distance = AMapUtils.calculateLineDistance(userPoint, placePoint).toString()
            //转换距离格式
            val df = DecimalFormat()
            df.applyPattern("0.00")
            tvDistance.text = df.format(distance.toFloat() / 1000.00) + "km"
        }

        ivStar.setOnClickListener {
            if (inList) {
                presenter.delWishList(detailWish)

            } else {
                var wishToADD = WishList(0, mSessionManager.email, detail.name, 0, poiID, imageUrl, mSessionManager.district)
                presenter.addWishList(wishToADD)
            }
        }
    }

    /**
     * Called by presenter when ScenicSpotDetials are got unsuccessfully
     * @param error:Throwable
     * @author ZhentingYan
     */
    override fun showError(error: Throwable) {
        error.printStackTrace()
        if (ScenicSpotFragment.shakeJudge) {
            toast("这次没有摇到和你有缘分的景点哦，再试一次吧～")
        } else toast("这个景点比较小众，暂不支持查看详情噢～")
        this.finish()
    }

    /**
     * Called when del WithList succeed
     * @param detail：WishList
     * @author ZhentingYan
     */
    override fun deleteWishListSuccess(detail: WishList) {
        ivStar.setImageResource(R.drawable.unliked)
        tvAdd.text = "加入心愿单"
        applicationContext.toast("${detail.scene}骂骂咧咧地离开了你的心愿单")
        inList = false
    }

    /**
     * Called when add WithList succeed
     * @param detail：WishList
     * @author ZhentingYan
     */
    override fun addWishSuccess(detail: WishList) {
        ivStar.setImageResource(R.drawable.liked)
        tvAdd.text = "删除该心愿"
        applicationContext.toast("记得要去探索${detail.scene}哦!")
        inList = true
        detailWish = detail
    }

    /**
     * Called when query user WistLists succeed
     * @param detailList：List<WishList>
     * @author ZhentingYan
     */
    override fun getUserWishListsSuccess(detailList: List<WishList>) {
        //Select the wish related to the current POI
        if (detailList != null) {
            for (item in detailList) {
                if (item.poi.equals(poiID)) {
                    inList = true
                    detailWish = item
                    break
                }
            }
        }
        if (inList) {
            ivStar.setImageResource(R.drawable.liked)
            tvAdd.text = "删除该心愿"
        } else {
            ivStar.setImageResource(R.drawable.unliked)
            tvAdd.text = "加入心愿单"
        }
    }

    /**
     * Called when query user WistLists succeed
     * @param detailList：List<WishList>
     * @author ZhentingYan
     */
    override fun showSqlError(error: SQLException) {
        applicationContext.toast("心愿信息获取失败,错误信息:${error.message}")
    }

    override fun onDestroy() {
        super.onDestroy()
        ScenicSpotFragment.shakeJudge = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.fragment_scenicspot_desc)
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        mMap = mapView.map
        AMapInit()
        val intent = getIntent()
        mSessionManager = SessionManager(applicationContext)
        poiID = intent.getStringExtra("id")
        imageUrl = intent.getStringExtra("picAddr")
        presenter.loadScenicSpotDetail(poiID)
        presenter.getUserWishList(mSessionManager.email)

    }

    /**
     * Used for AMapInit
     * @author ZhentingYan
     */
    fun AMapInit() {
        myLocationStyle = MyLocationStyle()//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(60000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mMap.setMyLocationStyle(myLocationStyle)//设置定位蓝点的Style
        mMap.setMyLocationEnabled(true)// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }
}
