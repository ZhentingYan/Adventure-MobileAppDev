package com.tongjisse.adventure.view.main

import android.content.Intent
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import com.tongjisse.adventure.R
import com.tongjisse.adventure.dao.WishListDao
import com.tongjisse.adventure.model.bean.WishList
import com.tongjisse.adventure.view.common.ItemAdapter
import com.tongjisse.adventure.view.common.bindView
import com.tongjisse.adventure.view.common.loadImage
import com.tongjisse.adventure.view.common.toast

class MainWishListAdapter(
        val wishList: WishList
): ItemAdapter<MainWishListAdapter.ViewHolder>(R.layout.main_wishlist_adapter_item) {

    override fun ViewHolder.onBindViewHolder() {
        checkBox.text=wishList.scene
        if(wishList.state==1){
            checkBox.isChecked=true
            checkBox.paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        checkBox.isEnabled=false

    }
    override fun onCreateViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val checkBox by bindView<CheckBox>(R.id.cbMainWish)
    }
}
