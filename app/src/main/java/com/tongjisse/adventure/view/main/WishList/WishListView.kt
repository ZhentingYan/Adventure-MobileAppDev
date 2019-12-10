package com.tongjisse.adventure.view.main.WishList

import com.tongjisse.adventure.data.bean.WishList
import java.sql.SQLException

interface WishListView {
    fun getUserWishListsSuccess(userWishList: List<WishList>)
    fun getUserWishListsFailed(error: SQLException)
}