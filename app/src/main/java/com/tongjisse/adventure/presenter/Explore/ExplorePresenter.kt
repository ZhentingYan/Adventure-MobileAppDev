package com.tongjisse.adventure.presenter.Explore

import com.tongjisse.adventure.model.dao.WishListDao
import com.tongjisse.adventure.presenter.BasePresenter
import com.tongjisse.adventure.view.main.Explore.ExploreView
import java.sql.SQLException

class ExplorePresenter(
        val view: ExploreView
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