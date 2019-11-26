package com.tongjisse.adventure.view.views.ScenicSpot

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.se.omapi.Session
import android.view.Window
import com.amap.api.maps.AMap
import com.tongjisse.adventure.data.network.ScenicSpotRepository
import com.tongjisse.adventure.model.ScenicSpotDetail
import com.tongjisse.adventure.presenter.ScenicSpotDetailPresenter
import com.tongjisse.adventure.view.common.BaseActivityWithPresenter
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.ScenicSpotDetailView
import com.tongjisse.adventure.R
import kotlinx.android.synthetic.main.fragment_scenicspot_desc.*
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.tongjisse.adventure.view.common.loadImage
import com.amap.api.maps.model.MyLocationStyle
import com.tongjisse.adventure.dao.WishListDao
import com.tongjisse.adventure.model.bean.WishList
import com.tongjisse.adventure.utils.SessionManager


class ScenicSpotDetailActivity : BaseActivityWithPresenter(),ScenicSpotDetailView {
    private lateinit var mMap:AMap
    private lateinit var imageUrl:String
    private lateinit var userSP: SharedPreferences
    var inList=false
    private lateinit var poiID:String
    private lateinit var detailWish:WishList
    private val wishListDao=WishListDao()
    private lateinit var mSessionManager:SessionManager
    override val presenter by lazy{ ScenicSpotDetailPresenter(this,ScenicSpotRepository.get())}
    override fun show(items: List<ScenicSpotDetail>) {
        val detail=items[0]
        val myLocationStyle: MyLocationStyle
        myLocationStyle = MyLocationStyle()//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(60000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mMap.setMyLocationStyle(myLocationStyle)//设置定位蓝点的Style
        mMap.setMyLocationEnabled(true)// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        val marker = mMap.addMarker(MarkerOptions().position(LatLng(detail.latitude.toDouble(), detail.longitude.toDouble())).title(detail.name).snippet(detail.address))
        tvAddress.text=detail.address
        tvPublisher.text=detail.intro
        tvType.text=detail.type
        ivWish.loadImage(imageUrl)
        tvStoryTitle.text=detail.name
        val userPoint=LatLng(userSP.getString("LATITUDE", " ").toDouble() ,userSP.getString("LONGTITUDE", " ").toDouble())
        val placePoint=LatLng(detail.latitude.toDouble(),detail.longitude.toDouble())
        val distance = AMapUtils.calculateLineDistance(userPoint,placePoint)
        tvDistance.text=(distance/1000.00).toString()+"km"
        ivStar.setOnClickListener {
            if(inList){
                wishListDao.delInfo(detailWish)
                ivStar.setImageResource(R.drawable.unliked)
                tvAdd.text="加入心愿单"
                it.context.toast("您已经将"+detail.name+"从心愿单中删除了...")
                inList=false

            }else{
                var wishToADD=WishList(0,mSessionManager.email,detail.name,0,poiID,imageUrl,mSessionManager.district)
                wishListDao.addWish(wishToADD)
                ivStar.setImageResource(R.drawable.liked)
                tvAdd.text="删除该心愿"
                it.context.toast("您已经成功将"+detail.name+"加入心愿单咯～！")
                inList=true
            }
        }
    }

    override fun showError(error: Throwable) {
        error.printStackTrace()
        //tvDesc.text="这个景点比较小众，没有相关介绍哦～"
        if(ScenicSpotFragment.shakeJudge) {
            toast("这次没有摇到和你有缘分的景点哦，再试一次吧～")
            ScenicSpotFragment.shakeJudge=false
        }
        else toast("这个景点比较小众，暂不支持查看详情噢～")
        this.finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.fragment_scenicspot_desc)
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        mMap=mapView.map
        val intent=getIntent()
        userSP = applicationContext.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        mSessionManager=SessionManager(applicationContext)
        poiID=intent.getStringExtra("id")
        imageUrl=intent.getStringExtra("picAddr")
        presenter.loadScenicSpotDetail(poiID)
        var userWishList=wishListDao.queryByUser(mSessionManager.email)
        if(userWishList!=null) {
            for (item in userWishList) {
                if (item.poi.equals(poiID)) {
                    inList = true
                    detailWish = item
                    break
                }
            }
        }
        if(inList){
            ivStar.setImageResource(R.drawable.liked)
            tvAdd.text="删除该心愿"
        }else{
            ivStar.setImageResource(R.drawable.unliked)
            tvAdd.text="加入心愿单"
        }

    }
}
