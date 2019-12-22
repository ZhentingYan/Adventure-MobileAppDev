package com.tongjisse.adventure.data.network

import com.tongjisse.adventure.data.Provider
import com.tongjisse.adventure.data.ScenicSpotRepositoryImpl
import com.tongjisse.adventure.model.ScenicSpotDetail
import com.tongjisse.adventure.model.ScenicSpotGallery
import io.reactivex.Single

interface ScenicSpotRepository {
    fun getScenicSpotDetails(id: String?): Single<List<ScenicSpotDetail>>
    fun getScenicSpots(types: String?, city: String?, page: String): Single<List<ScenicSpotGallery>>
    fun getScenicSpotsWithKeywords(types: String?, city: String?, keywords: String?): Single<List<ScenicSpotGallery>>

    companion object : Provider<ScenicSpotRepository>() {
        override fun creator() = ScenicSpotRepositoryImpl()
    }
}