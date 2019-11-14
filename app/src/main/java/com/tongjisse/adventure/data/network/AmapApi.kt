package com.tongjisse.adventure.data.network

import com.tongjisse.adventure.data.network.dto.DataWrapper
import com.tongjisse.adventure.data.network.dto.ScenicSpotDetailDto
import com.tongjisse.adventure.data.network.dto.ScenicSpotDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AmapApi {
    @GET("/v3/place/detail")
    fun getScenicSpotDetails(@Query("id") id: String?
    ): Single<DataWrapper<List<ScenicSpotDetailDto>>>

    @GET("/v3/place/text")
    fun getScenicSpots(@Query("types") types: String?,
                       @Query("city") city:String?
    ): Single<DataWrapper<List<ScenicSpotDto>>>

    @GET("/v3/place/text")
    fun getScenicSpotsWithKeywords(@Query("types") types: String?,
                                   @Query("city") city:String?,
                                   @Query("keywords") keywords:String?
    ): Single<DataWrapper<List<ScenicSpotDto>>>

}