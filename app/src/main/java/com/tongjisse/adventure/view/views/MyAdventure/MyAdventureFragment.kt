package com.tongjisse.adventure.view.views.MyAdventure

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import com.tongjisse.adventure.R
import com.tongjisse.adventure.presenter.MyAdventure.MyAdventurePresenter
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.BaseFragmentWithPresenter
import com.tongjisse.adventure.view.common.loadImage
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.MyAdventure.MyAdventureView
import com.tongjisse.adventure.view.views.Image.SelectDialog
import com.tongjisse.adventure.view.views.Main.MenuActivity
import com.tongjisse.adventure.view.views.Story.UserStoryActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import java.sql.SQLException


/**
 * @modified: Add funcs about avatar By Feifan Wang
 */
class MyAdventureFragment : BaseFragmentWithPresenter(), MyAdventureView {

    private val REQUEST_CODE_SELECT = 100
    private val REQUEST_CODE_PREVIEW = 101
    lateinit var mSessionManager: SessionManager
    override val presenter by lazy { MyAdventurePresenter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSessionManager = SessionManager(context)
        presenter.getAvatar(mSessionManager.email)

        tvName.text = "${mSessionManager.firstName} ${mSessionManager.lastName}"

        llLogOut.setOnClickListener {
            mSessionManager.logoutUser()
        }
        llMyStory.setOnClickListener {
            val intent = Intent(context, UserStoryActivity::class.java)
            startActivity(intent)
        }
        llShake.setOnClickListener {
            MenuActivity.sectionTab.getTabAt(1)!!.select();
            context!!.toast("试试摇一摇你的手机，会有适合的景点推荐给你哦！")
        }
        llMyWishList.setOnClickListener {
            MenuActivity.sectionTab.getTabAt(3)!!.select();
        }
        tvEdit.setOnClickListener {
            val intent = Intent(it.context, UserProfileActivity::class.java)
            it.context.startActivity(intent)
        }
        ivProfilePic.setOnClickListener() {
            val names = ArrayList<String>()
            names.add("拍照")
            names.add("相册")
            showDialog(object : SelectDialog.SelectDialogListener {
                override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    when (position) {
                        0 // 直接调起相机
                        -> {
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().selectLimit = 1
                            ImagePicker.getInstance().isMultiMode = false
                            ImagePicker.getInstance().style = CropImageView.Style.CIRCLE
                            ImagePicker.getInstance().isSaveRectangle = false
                            val intent = Intent(activity, ImageGridActivity::class.java)
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true) // 是否是直接打开相机
                            startActivityForResult(intent, REQUEST_CODE_SELECT)
                        }
                        1 -> {
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().selectLimit = 1
                            ImagePicker.getInstance().isMultiMode = false
                            ImagePicker.getInstance().style = CropImageView.Style.CIRCLE
                            ImagePicker.getInstance().isSaveRectangle = false
                            val intent1 = Intent(activity, ImageGridActivity::class.java)
                            startActivityForResult(intent1, REQUEST_CODE_SELECT)
                        }
                    }
                }
            }, names)
        }
    }

    /**
     * 弹出选择对话框（拍照/相册）
     *
     * @author Feifan Wang
     */
    private fun showDialog(listener: SelectDialog.SelectDialogListener, names: List<String>): SelectDialog {
        val dialog = SelectDialog(activity as Activity, R.style
                .transparentFrameWindowStyle,
                listener, names)
        if (!this.isRemoving) {
            dialog.show()
        }
        return dialog
    }

    @Suppress("UNCHECKED_CAST")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var images: ArrayList<ImageItem>?
        //添加图片返回，此处不提供预览功能
        if (data != null && requestCode == REQUEST_CODE_SELECT) {
            images = data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<ImageItem>
            if (images.isNotEmpty()) {
                presenter.updateAvatar(images.get(0), mSessionManager.email)
            }
        }
    }

    override fun updateAvatarSuccess(avatar: ImageItem) {
        ivProfilePic.loadImage(avatar.path)
        context!!.toast("修改头像成功！")
    }

    override fun updateAvatarFailed(error: SQLException) {
        context!!.toast("修改头像失败！请记录错误信息：" + error)
    }

    override fun getAvatarSuccess(avatar: ImageItem) {
        if (avatar.path != null) ivProfilePic.loadImage(avatar.path)
    }

    override fun getAvatarFailed(error: SQLException) {
        context!!.toast("加载头像失败！请记录错误信息：" + error)
    }

    /**
     * Refresh to keep profile updated
     *
     * @author Feifan Wang
     */
    override fun onResume() {
        super.onResume()
        tvName.text = "${mSessionManager.firstName} ${mSessionManager.lastName}"
    }
}