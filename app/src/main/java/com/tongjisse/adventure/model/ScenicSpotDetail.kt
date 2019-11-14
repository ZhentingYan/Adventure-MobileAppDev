package com.tongjisse.adventure.model

import android.util.Log
import com.tongjisse.adventure.data.network.dto.ScenicSpotDetailDto

data class ScenicSpotDetail(
        val type:String,
        val address:String,
        val id: String,
        val name: String,
        val longitude: String,
        val latitude:String,
        val intro:String?
) {
    constructor(dto: ScenicSpotDetailDto) : this(
            id=dto.id,
            name=dto.name,
            longitude = dto.location.split(",")!![0],
            latitude = dto.location.split(",")!![1],
            address = dto.address,
            intro=dto!!.intro,
            type=dto.type
    )
}

