package com.tongjisse.adventure.view.main

import com.tongjisse.adventure.model.ScenicSpotGallery

interface ScenicSpotView{
    var refresh:Boolean
    fun show(items:List<ScenicSpotGallery>)
    fun showError(error:Throwable)
}