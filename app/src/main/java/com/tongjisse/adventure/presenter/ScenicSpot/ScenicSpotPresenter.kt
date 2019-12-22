package com.tongjisse.adventure.presenter.ScenicSpot

import com.tongjisse.adventure.data.applySchedulers
import com.tongjisse.adventure.data.network.ScenicSpotRepository
import com.tongjisse.adventure.data.plusAssign
import com.tongjisse.adventure.data.subscribeBy
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.ScenicSpot.ScenicSpotView

class ScenicSpotPresenter(
        val view: ScenicSpotView,
        val repository: ScenicSpotRepository
) : BasePresenter() {

    fun loadScenicSpots(city: String?, page: String) {
        val result = this.repository.getScenicSpots("110000", city, page)
        subscription += result
                .applySchedulers()
                .subscribeBy(
                        onSuccess = view::show,
                        onError = view::showError
                )
    }

    fun loadScenicSpotsWithKeywords(city: String?, keywords: String?) {
        val result = this.repository.getScenicSpotsWithKeywords("110000", city, keywords)
        subscription += result
                .applySchedulers()
                .subscribeBy(
                        onSuccess = view::show,
                        onError = view::showError
                )
    }

}