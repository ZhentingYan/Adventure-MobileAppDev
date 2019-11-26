package com.tongjisse.adventure.view.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.tongjisse.adventure.model.ScenicSpotGallery
import com.tongjisse.adventure.view.common.ItemAdapter
import com.tongjisse.adventure.R
import com.tongjisse.adventure.view.common.bindView
import com.tongjisse.adventure.view.common.loadImage
import android.content.Intent
import com.tongjisse.adventure.view.views.ScenicSpot.ScenicSpotDetailActivity
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng
import com.tongjisse.adventure.view.views.ScenicSpot.ScenicSpotFragment


class ScenicSpotGalleryAdapter(
        val scenicSpot:ScenicSpotGallery
):ItemAdapter<ScenicSpotGalleryAdapter.ViewHolder>(R.layout.gallery_adapter_item){

    override fun ViewHolder.onBindViewHolder() {
        tvSceneName.text=scenicSpot.name
        ivPhoto.loadImage(scenicSpot.imageurl)

        //Log.e("test",scenicSpot.name)
        val location = ScenicSpotFragment.locationClient!!.getLastKnownLocation()
        val userPoint=LatLng(location.latitude,location.longitude)
        val placePoint=LatLng(scenicSpot.latitude.toDouble(),scenicSpot.longitude.toDouble())
        val distance = AMapUtils.calculateLineDistance(userPoint,placePoint)
        tvDistance.text=(distance/1000.00).toString()+"km"
        tvType.text=scenicSpot.type
        ivPhoto.setOnClickListener {
            val intent = Intent(it.context, ScenicSpotDetailActivity::class.java)
            intent.putExtra("id", scenicSpot.id);   //键值对
            intent.putExtra("picAddr", scenicSpot.imageurl);   //键值对
            it.context.startActivity(intent)
        }
        tvSceneName.setOnClickListener {
            val intent = Intent(it.context, ScenicSpotDetailActivity::class.java)
            intent.putExtra("id", scenicSpot.id);   //键值对
            intent.putExtra("picAddr", scenicSpot.imageurl);   //键值对
            it.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val tvSceneName by bindView<TextView>(R.id.tvSceneName)
        val ivPhoto by bindView<ImageView>(R.id.ivWish)
        val tvDistance by bindView<TextView>(R.id.tvDistance)
        val tvType by bindView<TextView>(R.id.tvType)

    }

}
