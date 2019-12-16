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

/**
 * Set up adapter for story recycler view
 *
 * @author Feifan Wang
 */
class StoryListAdapter(
        val storyList: StoryList
) : ItemAdapter<StoryListAdapter.ViewHolder>(R.layout.story_adapter_item) {

    val storyListDao = StoryListDao()

    override fun ViewHolder.onBindViewHolder() {
        tvFirstName.text = storyList.user.firstName
        lastName.text = storyList.user.lastName
        tvStoryTitle.text = storyList.title
        tvDate.text = storyList.time.split(" ").get(0)
        ivStory.loadImage(storyList.photo.path)
        ivPublisher.loadImage(storyList.user.avatar.path)


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
        val tvFirstName by bindView<TextView>(R.id.tvFirstName)
        val lastName by bindView<TextView>(R.id.tvLastName)
        val tvStoryTitle by bindView<TextView>(R.id.tvStoryTitle)
        val tvDate by bindView<TextView>(R.id.tvDate)
        val ivStory by bindView<ImageView>(R.id.ivStory)
        val ivPublisher by bindView<ImageView>(R.id.ivPublisher)

    }
}