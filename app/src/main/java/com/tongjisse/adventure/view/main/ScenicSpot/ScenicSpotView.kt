package com.tongjisse.adventure.view.main.ScenicSpot

import com.tongjisse.adventure.model.ScenicSpotGallery

interface ScenicSpotView {
    fun show(items: List<ScenicSpotGallery>)
    fun showError(error: Throwable)
}