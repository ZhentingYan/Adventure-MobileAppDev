package com.tongjisse.adventure.view.main

import com.tongjisse.adventure.model.ScenicSpotDetail

interface ScenicSpotDetailView{
    // var refresh:Boolean
    fun show(items:List<ScenicSpotDetail>)
    fun showError(error:Throwable)
}