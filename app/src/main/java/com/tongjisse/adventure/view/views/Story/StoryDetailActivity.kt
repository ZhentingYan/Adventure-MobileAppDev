package com.tongjisse.adventure.view.views.Story

import android.app.Activity
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.View
import android.view.Window
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.StoryList
import com.tongjisse.adventure.presenter.Story.StoryDetailPresenter
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.BaseActivityWithPresenter
import com.tongjisse.adventure.view.common.getIntent
import com.tongjisse.adventure.view.common.loadImage
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.Story.StoryDetailView
import kotlinx.android.synthetic.main.activity_story_detail.*
import kotlinx.android.synthetic.main.fragment_scenicspot_desc.*
import java.sql.SQLException

class StoryDetailActivity : BaseActivityWithPresenter(), StoryDetailView {

    private lateinit var story: StoryList
    private lateinit var mSessionManager: SessionManager
    override val presenter by lazy { StoryDetailPresenter(this) }

    override fun show() {
        ivStory.loadImage(story.photo.path)
        tvStoryTitle.text=story.title
        tvPublishDate.text = story.time
        tvStoryPublisher.text = story.user.firstName+story.user.lastName
        tvContent.text = story.content
        tvPlace.text = story.place
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_story_detail)
        val intent = getIntent()
        mSessionManager = SessionManager(applicationContext)
        presenter.getStory(intent.getStringExtra("id"))
        show()
        if(mSessionManager.email.equals(story.user.emailAddress))
          llOwner.visibility= View.VISIBLE
        ivDelete.setOnClickListener {
            presenter.delStoryList(story)
        }
        ivEdit.setOnClickListener {
            val intent = Intent(it.context, StoryPublishActivity::class.java)
            intent.putExtra("story", story)
            it.context.startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun deleteStoryListSuccess(detail: StoryList) {
        applicationContext.toast("删除游记成功！")
        val intent = getIntent()
        intent.putExtra("del", true)
        setResult(Activity.RESULT_OK, intent)
        this.finish()
    }

    override fun addStorySuccess(detail: StoryList) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStorySuccess(detail: StoryList) {
        if (detail != null) {
            story = detail
        }
    }

    override fun showError(error: Throwable) {
        error.printStackTrace()
    }


    override fun showSqlError(error: SQLException) {
        applicationContext.toast("游记信息获取失败,错误信息:${error.message}")
    }
}