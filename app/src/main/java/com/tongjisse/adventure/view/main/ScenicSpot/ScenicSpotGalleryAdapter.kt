package com.tongjisse.adventure.view.main.ScenicSpot

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng
import com.tongjisse.adventure.R
import com.tongjisse.adventure.model.ScenicSpotGallery
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.ItemAdapter
import com.tongjisse.adventure.view.common.bindView
import com.tongjisse.adventure.view.common.loadImage
import com.tongjisse.adventure.view.views.ScenicSpot.ScenicSpotDetailActivity
import java.text.DecimalFormat


class ScenicSpotGalleryAdapter(
        val scenicSpot: ScenicSpotGallery
) : ItemAdapter<ScenicSpotGalleryAdapter.ViewHolder>(R.layout.gallery_adapter_item) {
    lateinit var mSessionManager: SessionManager
    override fun ViewHolder.onBindViewHolder() {
        tvSceneName.text = scenicSpot.name
        ivPhoto.loadImage(scenicSpot.imageurl)
        //Log.e("test",scenicSpot.name)
        mSessionManager = SessionManager(ivPhoto.context)
        if (mSessionManager.latitude.equals("") || mSessionManager.longitude.equals(""))
            tvDistance.text = "获取定位信息失败..."
        else {
            val userPoint = LatLng(mSessionManager.latitude.toDouble(), mSessionManager.longitude.toDouble())
            val placePoint = LatLng(scenicSpot.latitude.toDouble(), scenicSpot.longitude.toDouble())
            val distance = AMapUtils.calculateLineDistance(userPoint, placePoint)
            val df = DecimalFormat()
            df.applyPattern("0.00")
            tvDistance.text = df.format(distance.toFloat() / 1000.00) + "km"
        }
        tvType.text = scenicSpot.type
        ivPhoto.setOnClickListener {
            val intent = Intent(it.context, ScenicSpotDetailActivity::class.java)
            intent.putExtra("id", scenicSpot.id);   //传入POI id
            intent.putExtra("picAddr", scenicSpot.imageurl);  //传入图片地址
            it.context.startActivity(intent)
        }
        tvSceneName.setOnClickListener {
            val intent = Intent(it.context, ScenicSpotDetailActivity::class.java)
            intent.putExtra("id", scenicSpot.id);  //传入POI id
            intent.putExtra("picAddr", scenicSpot.imageurl);   //传入图片地址
            it.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSceneName by bindView<TextView>(R.id.tvSceneName)
        val ivPhoto by bindView<ImageView>(R.id.ivWish)
        val tvDistance by bindView<TextView>(R.id.tvDistance)
        val tvType by bindView<TextView>(R.id.tvType)
    }
}
