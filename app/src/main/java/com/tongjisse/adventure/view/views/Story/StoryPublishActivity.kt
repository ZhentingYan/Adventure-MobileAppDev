package com.tongjisse.adventure.view.views.Story

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.Toast
import com.isseiaoki.simplecropview.FreeCropImageView
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.ui.ImagePreviewDelActivity
import com.lzy.imagepicker.view.CropImageView
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.StoryList
import com.tongjisse.adventure.data.bean.UserInfo
import com.tongjisse.adventure.presenter.Story.StoryPublishPresenter
import com.tongjisse.adventure.utils.CityPickerHelper
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.utils.TimeUtils
import com.tongjisse.adventure.view.common.BaseActivityWithPresenter
import com.tongjisse.adventure.view.common.loadImage
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.Story.StoryPublishView
import com.tongjisse.adventure.view.views.Image.SelectDialog
import kotlinx.android.synthetic.main.activity_publish_story.*
import java.sql.SQLException

/**
 * Publish new story or modify old story
 *
 * @author Feifan Wang
 */
class StoryPublishActivity : BaseActivityWithPresenter(), StoryPublishView {
    private val REQUEST_CODE_SELECT = 100
    private val REQUEST_CODE_PREVIEW = 101
    private var selImage = ArrayList<ImageItem>() //当前选择的所有图片
    private val maxImgCount = 1               //允许选择图片最大数
    private var story = StoryList()
    private lateinit var user: UserInfo
    private lateinit var mSessionManager: SessionManager
    private lateinit var mCityPickerHelper: CityPickerHelper
    private var isEdit = false
    override val presenter by lazy { StoryPublishPresenter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_publish_story)
        mSessionManager = SessionManager(applicationContext)
        mCityPickerHelper = CityPickerHelper(this, object : OnCityItemClickListener() {
            override fun onSelected(province: ProvinceBean?, city: CityBean?, district: DistrictBean?) {
                mSessionManager.refineLocation(province!!.name, city!!.name, district!!.name, mSessionManager.longitude, mSessionManager.latitude)
                tvDistrictSelect.text = mSessionManager.defaultAddress
            }
        })
        presenter.getUser(mSessionManager.email)

        ivPickImg.setOnClickListener() {
            val names = ArrayList<String>()
            names.add("拍照")
            names.add("相册")
            showDialog(object : SelectDialog.SelectDialogListener {
                override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    when (position) {
                        0 // 直接调起相机
                        -> {
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().selectLimit = maxImgCount
                            ImagePicker.getInstance().isMultiMode = false
                            ImagePicker.getInstance().style = CropImageView.Style.RECTANGLE
                            ImagePicker.getInstance().setFreeCrop(true, FreeCropImageView.CropMode.FREE)
                            val intent = Intent(this@StoryPublishActivity, ImageGridActivity::class.java)
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true) // 是否是直接打开相机
                            startActivityForResult(intent, REQUEST_CODE_SELECT)
                        }
                        1 -> {
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().selectLimit = maxImgCount
                            ImagePicker.getInstance().isMultiMode = false
                            ImagePicker.getInstance().style = CropImageView.Style.RECTANGLE
                            ImagePicker.getInstance().setFreeCrop(true, FreeCropImageView.CropMode.FREE)
                            val intent1 = Intent(this@StoryPublishActivity, ImageGridActivity::class.java)
                            startActivityForResult(intent1, REQUEST_CODE_SELECT)
                        }
                    }
                }
            }, names)
        }
        ivPickedImg.setOnClickListener() {
            //打开预览
            val intentPreview = Intent(this, ImagePreviewDelActivity::class.java)
            intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, selImage)
            intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0)
            intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true)
            startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW)
        }
        if (getIntent().getSerializableExtra("story") != null) {
            isEdit = true
            story = getIntent().getSerializableExtra("story") as StoryList
            activityInit(story)
        }
        bPublish.setOnClickListener() {
            story.user = user
            story.district = tvDistrictSelect.text.toString()
            if (selImage != null) {
                story.photo = selImage!!.get(0)
            }
            story.title = etTitle.text.toString()
            story.scene = etPlace.text.toString()
            story.content = etContent.text.toString()
            story.time = TimeUtils.getNow()

            if (isEdit) {
                updateStory(story)
            } else {
                addStory(story)
            }
        }
        if (!mSessionManager.district.equals(""))
            tvDistrictSelect.text = mSessionManager.defaultAddress
        else tvDistrictSelect.text = "无定位信息，请选择地址"
        tvDistrictSelect.setOnClickListener {
            mCityPickerHelper.showJD()
        }
    }

    /**
     * 初始化数据
     *
     * @param story: StoryList
     */
    private fun activityInit(story: StoryList) {
        etTitle.setText(story.title)
        tvDistrictSelect.setText(story.district)
        etContent.setText(story.content)
        ivPickImg.visibility = View.GONE
        ivPickedImg.visibility = View.VISIBLE
        ivPickedImg.loadImage(story.photo.path)
        selImage.add(story.photo)
        etPlace.setText(story.scene)
    }

    /**
     * 检验数据并新增游记故事
     *
     * @param story: StoryList
     */
    private fun addStory(story: StoryList) {
        var isCompleted = true
        when {
            story.title == "" -> {
                Toast.makeText(this, "发布失败！标题怎么空了╰(*°▽°*)╯", Toast.LENGTH_SHORT).show()
                isCompleted = false
            }
            story.district == "" -> {
                Toast.makeText(this, "发布失败！您去哪里旅行了╰(*°▽°*)╯", Toast.LENGTH_SHORT).show()
                isCompleted = false
            }
            story.content == "" -> {
                Toast.makeText(this, "发布失败！您游记的内容凭空消失了╰(*°▽°*)╯", Toast.LENGTH_SHORT).show()
                isCompleted = false
            }
            story.photo == ImageItem() -> {
                Toast.makeText(this, "发布失败！您的图片好像没有上传成功╰(*°▽°*)╯", Toast.LENGTH_SHORT).show()
                isCompleted = false
            }
            story.scene == "" -> {
                Toast.makeText(this, "发布失败！请填写相关景点[○･｀Д´･ ○]", Toast.LENGTH_SHORT).show()
                isCompleted = false
            }
        }
        if (isCompleted) {
            presenter.addStory(story)
        }
    }

    /**
     * 检验数据并修改游记故事
     *
     * @param story: StoryList
     */
    private fun updateStory(story: StoryList) {
        var isCompleted = true
        when {
            story.title == "" -> {
                Toast.makeText(this, "修改失败！标题怎么空了╰(*°▽°*)╯", Toast.LENGTH_SHORT).show()
                isCompleted = false
            }
            story.district == "" -> {
                Toast.makeText(this, "修改失败！您去哪里旅行了╰(*°▽°*)╯", Toast.LENGTH_SHORT).show()
                isCompleted = false
            }
            story.content == "" -> {
                Toast.makeText(this, "修改失败！您游记的内容凭空消失了╰(*°▽°*)╯", Toast.LENGTH_SHORT).show()
                isCompleted = false
            }
            story.photo == ImageItem() -> {
                Toast.makeText(this, "修改失败！您的图片好像没有上传成功╰(*°▽°*)╯", Toast.LENGTH_SHORT).show()
                isCompleted = false
            }
            story.scene == "" -> {
                Toast.makeText(this, "修改失败！请填写相关景点[○･｀Д´･ ○]", Toast.LENGTH_SHORT).show()
                isCompleted = false
            }
        }
        if (isCompleted) {
            presenter.updateStory(story)
        }
    }

    private fun showDialog(listener: SelectDialog.SelectDialogListener, names: List<String>): SelectDialog {
        val dialog = SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names)
        if (!this.isFinishing) {
            dialog.show()
        }
        return dialog
    }

    /**
     * 处理图片上传结果
     *
     */
    @Suppress("UNCHECKED_CAST")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var images: ArrayList<ImageItem>?
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<ImageItem>
                if (images.isNotEmpty()) {
                    selImage = images
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS) as ArrayList<ImageItem>
                if (images.isEmpty()) {
                    selImage = images
                }
            }
        }

        changeImg()
    }

    fun changeImg() {
        if (selImage != null && selImage.isNotEmpty()) {
            ivPickedImg.visibility = View.VISIBLE
            ivPickImg.visibility = View.GONE
            ImagePicker.getInstance().imageLoader.displayImage(this, selImage!!.get(0).path, ivPickedImg, 0, 0)
        } else {
            ivPickedImg.visibility = View.GONE
            ivPickImg.visibility = View.VISIBLE
        }
    }

    override fun addStorySuccess() {
        applicationContext.toast("发表游记成功！记得多分享你旅途中的故事哦o(^_^)o")
        finish()
    }

    override fun addStoryFailed(error: SQLException) {
        applicationContext.toast("发表游记失败！请检查格式是否正确QAQ")
    }

    override fun updateStorySuccess() {
        applicationContext.toast("修改游记成功！期待您的游记越来越好哦o(^_^)o")
        finish()
    }

    override fun updateStoryFailed(error: SQLException) {
        applicationContext.toast("修改游记失败！请检查格式是否正确QAQ")
    }


    override fun getUserInfo(user: UserInfo) {
        this.user = user
    }

    override fun userSqlError(error: SQLException) {
        applicationContext.toast("受到神秘力量的影响，获取用户信息失败......")
    }
}