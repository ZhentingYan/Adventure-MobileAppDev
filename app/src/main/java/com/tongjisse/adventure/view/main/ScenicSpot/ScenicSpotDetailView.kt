package com.tongjisse.adventure.view.main.ScenicSpot

import com.tongjisse.adventure.data.bean.WishList
import com.tongjisse.adventure.model.ScenicSpotDetail
import java.sql.SQLException

interface ScenicSpotDetailView {
    // var refresh:Boolean
    fun show(items: List<ScenicSpotDetail>)

    fun showError(error: Throwable)
    fun deleteWishListSuccess(detail: WishList)
    fun addWishSuccess(detail: WishList)
    fun showSqlError(error: SQLException)
    fun getUserWishListsSuccess(detailList: List<WishList>)
}
