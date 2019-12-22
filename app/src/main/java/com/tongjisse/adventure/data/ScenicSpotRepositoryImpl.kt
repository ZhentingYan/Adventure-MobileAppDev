package com.tongjisse.adventure.data

import com.tongjisse.adventure.data.network.AmapApi
import com.tongjisse.adventure.data.network.ScenicSpotRepository
import com.tongjisse.adventure.data.network.provider.retrofit
import com.tongjisse.adventure.model.ScenicSpotDetail
import com.tongjisse.adventure.model.ScenicSpotGallery
import io.reactivex.Single

class ScenicSpotRepositoryImpl : ScenicSpotRepository {
    val api = retrofit.create(AmapApi::class.java)
    override fun getScenicSpotDetails(id: String?): Single<List<ScenicSpotDetail>> = api.getScenicSpotDetails(
            id = id
    ).map {
        it.pois?.map(::ScenicSpotDetail)
    }

    override fun getScenicSpots(types: String?, city: String?, page: String): Single<List<ScenicSpotGallery>> = api.getScenicSpots(
            types = types,
            city = city,
            page = page
    ).map {
        it.pois?.map(::ScenicSpotGallery)
    }

    override fun getScenicSpotsWithKeywords(types: String?, city: String?, keywords: String?): Single<List<ScenicSpotGallery>> = api.getScenicSpotsWithKeywords(
            types = types,
            city = city,
            keywords = keywords
    ).map {
        it.pois?.map(::ScenicSpotGallery)
    }

    companion object {
        const val elementsOnListLimit = 50
    }
}