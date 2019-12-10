package com.tongjisse.adventure.presenter.WishList

import com.tongjisse.adventure.model.dao.WishListDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.WishList.WishListView
import java.sql.SQLException

class WishListPresenter(
        val view: WishListView
) : BasePresenter() {
    val wishListDao = WishListDao()
    fun showUserWishLists(email: String, district: String) {
        try {
            var userWishList = wishListDao.queryByUserAndDistrict(email, district)
            view.getUserWishListsSuccess(userWishList)
        } catch (error: SQLException) {
            view.getUserWishListsFailed(error)
        }
    }

}