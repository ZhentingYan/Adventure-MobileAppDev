package com.tongjisse.adventure.presenter.ScenicSpot


import com.tongjisse.adventure.data.applySchedulers
import com.tongjisse.adventure.data.bean.WishList
import com.tongjisse.adventure.data.network.ScenicSpotRepository
import com.tongjisse.adventure.data.plusAssign
import com.tongjisse.adventure.data.subscribeBy
import com.tongjisse.adventure.model.dao.WishListDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.ScenicSpot.ScenicSpotDetailView
import java.sql.SQLException

class ScenicSpotDetailPresenter(
        val view: ScenicSpotDetailView,
        val repository: ScenicSpotRepository
) : BasePresenter() {
    var wishListDao = WishListDao()
    fun loadScenicSpotDetail(id: String?) {
        val result = this.repository.getScenicSpotDetails(id)
        subscription += result
                .applySchedulers()
<<<<<<< HEAD
                // .doOnSubscribe{view.refresh=true}
                // .doFinally{view.refresh=false}
=======
                // .doOnSubscribe{listView.refresh=true}
                // .doFinally{listView.refresh=false}
>>>>>>> 07c9524f5977b3d98e481c08f7ea7e5c9196886f
                .subscribeBy(
                        onSuccess = view::show,
                        onError = view::showError
                )
    }

    fun addWishList(wishList: WishList) {
        try {
            wishListDao.addWish(wishList)
            view.addWishSuccess(wishList)
        } catch (error: SQLException) {
            view.showSqlError(error)
        }
    }

    fun delWishList(wishList: WishList) {
        try {
            wishListDao.delInfo(wishList)
            view.deleteWishListSuccess(wishList)
        } catch (error: SQLException) {
            view.showSqlError(error)
        }
    }

    fun getUserWishList(email: String) {
        try {
            var userWishList = wishListDao.queryByUser(email)
            view.getUserWishListsSuccess(userWishList)
        } catch (error: SQLException) {
            view.showSqlError(error)
        }

    }

}