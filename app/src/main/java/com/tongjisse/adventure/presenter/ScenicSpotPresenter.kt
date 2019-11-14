package com.tongjisse.adventure.presenter

import com.tongjisse.adventure.data.applySchedulers
import com.tongjisse.adventure.data.network.ScenicSpotRepository
import com.tongjisse.adventure.view.main.ScenicSpotView
import com.tongjisse.adventure.data.plusAssign
import com.tongjisse.adventure.data.subscribeBy

class ScenicSpotPresenter(
        val view:ScenicSpotView,
        val repository:ScenicSpotRepository
):BasePresenter(){

    fun loadScenicSpots(city:String?){
        val result=this.repository.getScenicSpots("110000",city)
        subscription += result
                .applySchedulers()
                .doOnSubscribe{view.refresh=true}
                .doFinally{view.refresh=false}
                .subscribeBy(
                        onSuccess = view::show,
                        onError=view::showError
                )
    }
    fun loadScenicSpotsWithKeywords(city:String?,keywords:String?){
        val result=this.repository.getScenicSpotsWithKeywords("110000",city,keywords)
        subscription += result
                .applySchedulers()
                .doOnSubscribe{view.refresh=true}
                .doFinally{view.refresh=false}
                .subscribeBy(
                        onSuccess = view::show,
                        onError=view::showError
                )
    }


}