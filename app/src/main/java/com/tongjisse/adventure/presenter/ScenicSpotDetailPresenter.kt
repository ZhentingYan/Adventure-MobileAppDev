package com.tongjisse.adventure.presenter


import com.tongjisse.adventure.data.applySchedulers
import com.tongjisse.adventure.data.network.ScenicSpotRepository
import com.tongjisse.adventure.view.main.ScenicSpotView
import com.tongjisse.adventure.data.plusAssign
import com.tongjisse.adventure.data.subscribeBy
import com.tongjisse.adventure.view.main.ScenicSpotDetailView

class ScenicSpotDetailPresenter(
        val view:ScenicSpotDetailView,
        val repository:ScenicSpotRepository
):BasePresenter(){

    fun loadScenicSpotDetail(id:String?){
        val result=this.repository.getScenicSpotDetails(id)
        subscription += result
                .applySchedulers()
                // .doOnSubscribe{view.refresh=true}
                // .doFinally{view.refresh=false}
                .subscribeBy(
                        onSuccess = view::show,
                        onError=view::showError
                )
    }
}