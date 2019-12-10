package com.tongjisse.adventure.view.main.WishList

import android.content.Intent
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.WishList
import com.tongjisse.adventure.model.dao.WishListDao
import com.tongjisse.adventure.view.common.ItemAdapter
import com.tongjisse.adventure.view.common.bindView
import com.tongjisse.adventure.view.common.loadImage
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.views.ScenicSpot.ScenicSpotDetailActivity

class WishListAdapter(
        val wishList: WishList
) : ItemAdapter<WishListAdapter.ViewHolder>(R.layout.wishlist_item_adapter) {
    val wishListDao = WishListDao()
    override fun ViewHolder.onBindViewHolder() {
        checkBox.text = wishList.scene
        ivPhoto.loadImage(wishList.photo)
        ivPhoto.setOnClickListener {
            val intent = Intent(it.context, ScenicSpotDetailActivity::class.java)
            intent.putExtra("id", wishList.poi);   //键值对
            intent.putExtra("picAddr", wishList.photo);   //键值对
            it.context.startActivity(intent)
        }
        if (wishList.state == 1) {
            checkBox.isChecked = true
            checkBox.isEnabled = false
            checkBox.paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            buttonView.context.toast("恭喜你完成了" + wishList.scene + "的心愿！")
            checkBox.isChecked = true
            checkBox.isEnabled = false
            checkBox.paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            wishListDao.updateState(wishList, 1)
        }

    }

    override fun onCreateViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox by bindView<CheckBox>(R.id.cbWish)
        val ivPhoto by bindView<ImageView>(R.id.ivWish)
    }
}
