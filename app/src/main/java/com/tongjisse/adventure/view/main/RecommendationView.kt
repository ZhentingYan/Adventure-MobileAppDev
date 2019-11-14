package com.tongjisse.adventure.view.main

import com.tongjisse.adventure.model.ScenicSpotDetail
import com.tongjisse.adventure.model.ScenicSpotGallery

interface RecommendationView{
    fun getGallery(items:List<ScenicSpotGallery>)
    fun showError(error:Throwable)
    fun getDetail(items:List<ScenicSpotDetail>)
}