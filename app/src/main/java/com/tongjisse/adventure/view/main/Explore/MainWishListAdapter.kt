package com.tongjisse.adventure.view.main.Explore

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.WishList
import com.tongjisse.adventure.view.common.ItemAdapter
import com.tongjisse.adventure.view.common.bindView


class MainWishListAdapter(
        val wishList: WishList
) : ItemAdapter<MainWishListAdapter.ViewHolder>(R.layout.main_wishlist_adapter_item) {
    /**
     * As MainWishListAdapter here,set CheckBox state and <del> line
     * @author ZhentingYan
     */
    override fun ViewHolder.onBindViewHolder() {
        checkBox.text = wishList.scene
        if (wishList.state == 1) {
            checkBox.isChecked = true
            checkBox.paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        checkBox.isEnabled = false
    }

    override fun onCreateViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    /**
     * Bind R.id.cbMainWish to checkBox
     * @author ZhentingYan
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox by bindView<CheckBox>(R.id.cbMainWish)
    }
}
