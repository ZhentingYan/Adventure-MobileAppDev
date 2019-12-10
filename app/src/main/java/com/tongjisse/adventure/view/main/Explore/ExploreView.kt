package com.tongjisse.adventure.view.main.Explore

import com.tongjisse.adventure.data.bean.WishList
import java.sql.SQLException

interface ExploreView {
    fun getUserWishListsSuccess(userWishList: List<WishList>)
    fun getUserWishListsFailed(error: SQLException)
}