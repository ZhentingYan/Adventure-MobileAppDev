package com.tongjisse.adventure.view.views.UserAuthentication.Registration

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.isseiaoki.simplecropview.FreeCropImageView
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.ui.ImagePreviewDelActivity
import com.lzy.imagepicker.view.CropImageView
import com.tongjisse.adventure.R
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.views.Image.SelectDialog
import kotlinx.android.synthetic.main.fragment_add_avatar.*

/**
 * Add avatar when create account
 *
 * @author Feifan Wang
 */
class RegisterAvatarFragment : Fragment() {
    private val REQUEST_CODE_SELECT = 100
    private val REQUEST_CODE_PREVIEW = 101
    private var selImage = ArrayList<ImageItem>() //当前选择的所有图片
    lateinit var mSessionManager: SessionManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_avatar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSessionManager = SessionManager(context)
        ivPickImg.visibility = View.VISIBLE
        ivPickedImg.visibility = View.GONE

        bNext.setOnClickListener {
            registrationProceed()
        }

        ivPickedImg.setOnClickListener() {
            //打开预览
            val intentPreview = Intent(context, ImagePreviewDelActivity::class.java)
            intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, selImage)
            intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0)
            intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true)
            startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW)
        }

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
                            ImagePicker.getInstance().selectLimit = 1
                            ImagePicker.getInstance().isMultiMode = false
                            ImagePicker.getInstance().style = CropImageView.Style.CIRCLE
                            ImagePicker.getInstance().isSaveRectangle = false
                            val intent = Intent(context, ImageGridActivity::class.java)
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true) // 是否是直接打开相机
                            startActivityForResult(intent, REQUEST_CODE_SELECT)
                        }
                        1 -> {
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().selectLimit = 1
                            ImagePicker.getInstance().isMultiMode = false
                            ImagePicker.getInstance().style = CropImageView.Style.CIRCLE
                            ImagePicker.getInstance().isSaveRectangle = false
                            val intent1 = Intent(context, ImageGridActivity::class.java)
                            startActivityForResult(intent1, REQUEST_CODE_SELECT)
                        }
                    }
                }
            }, names)
        }
    }

    fun showDialog(listener: SelectDialog.SelectDialogListener, names: List<String>): SelectDialog {
        val dialog = SelectDialog(activity as Activity, R.style
                .transparentFrameWindowStyle,
                listener, names)
        if (!this.isRemoving) {
            dialog.show()
        }
        return dialog
    }

    fun changeImg() {
        if (selImage != null && selImage.isNotEmpty()) {
            ivPickedImg.visibility = View.VISIBLE
            ivPickImg.visibility = View.GONE
            ImagePicker.getInstance().imageLoader.displayImage(activity, selImage!!.get(0).path, ivPickedImg, 0, 0)
        } else {
            ivPickedImg.visibility = View.GONE
            ivPickImg.visibility = View.VISIBLE
        }
    }

    /**
     * 处理图片上传结果
     *
     */
    @Suppress("UNCHECKED_CAST")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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

    /**
     * 处理结果，跳转至下一个Fragment
     *
     */
    fun registrationProceed() {
        if (selImage!!.isNotEmpty()) {
            AVATAR = selImage.get(0)

        }
        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.progressFragment, PhoneNumFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    companion object {
        var AVATAR = ImageItem()
    }
}