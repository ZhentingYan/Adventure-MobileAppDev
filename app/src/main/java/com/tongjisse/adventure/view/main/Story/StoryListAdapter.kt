package com.tongjisse.adventure.view.main.Story

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.amap.api.mapcore.util.it
import com.lzy.imagepicker.ImagePicker
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.StoryList
import com.tongjisse.adventure.model.dao.StoryListDao
import com.tongjisse.adventure.view.common.ItemAdapter
import com.tongjisse.adventure.view.common.bindView
import com.tongjisse.adventure.view.common.loadImage
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.views.Story.StoryDetailActivity

class StoryListAdapter(
        val storyList: StoryList
) : ItemAdapter<StoryListAdapter.ViewHolder>(R.layout.story_adapter_item) {

    val storyListDao = StoryListDao()


    override fun ViewHolder.onBindViewHolder() {
        firstName.text = storyList.user.firstName
        lastName.text = storyList.user.lastName
        title.text = storyList.title
        date.text = storyList.time.split(" ").get(0)
        ivStory.loadImage(storyList.photo.path)


        ivStory.setOnClickListener() {
            val intent = Intent(it.context, StoryDetailActivity::class.java)
            intent.putExtra("id", storyList.id);   //传入id
            it.context.startActivity(intent)
        }
    }


    override fun onCreateViewHolder(itemView: View): StoryListAdapter.ViewHolder {
        return ViewHolder(itemView)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstName by bindView<TextView>(R.id.tvFirstName)
        val lastName by bindView<TextView>(R.id.tvLastName)
        val title by bindView<TextView>(R.id.tvStoryTitle)
        val date by bindView<TextView>(R.id.tvDate)
        val ivStory by bindView<ImageView>(R.id.ivStory)
    }
}