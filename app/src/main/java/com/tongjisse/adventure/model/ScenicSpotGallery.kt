package com.tongjisse.adventure.model

import android.util.Log
import com.tongjisse.adventure.data.network.dto.ScenicSpotDto


data class ScenicSpotGallery(
        val id: String,
        val name: String,
        val type:String,
        val longitude: String,
        val latitude:String,
        val imageurl:String
) {
    constructor(dto: ScenicSpotDto) : this(
            id=dto.id,
            name=dto.name,
            type=dto.type,
            imageurl=dto.imageUrl,
            longitude = dto.location.split(",")!![0],
            latitude = dto.location.split(",")!![1]
    )
}