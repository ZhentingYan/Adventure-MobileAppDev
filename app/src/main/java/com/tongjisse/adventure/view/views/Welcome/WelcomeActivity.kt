package com.tongjisse.adventure.view.views.Welcome

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.view.CropImageView
import com.tongjisse.adventure.R
import com.tongjisse.adventure.utils.GlideImageLoaderHelper
import com.tongjisse.adventure.utils.OrmLiteHelper
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.views.Main.MenuActivity

class WelcomeActivity : AppCompatActivity() {
    val ormLiteHelper = OrmLiteHelper.createInstance(this)
    lateinit var mSessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
<<<<<<< HEAD
=======
        initImagePicker()
>>>>>>> 07c9524f5977b3d98e481c08f7ea7e5c9196886f
        mSessionManager = SessionManager(this)
        /**
         * 如果SharedPreferences存在Session信息，则直接登陆
         * 否则，进入欢迎界面
         * @author ZhentingYan
         */
        if (!mSessionManager.email.equals("")) {
            val intent = Intent(applicationContext, MenuActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            applicationContext!!.toast("${mSessionManager.firstName + mSessionManager.lastName}，欢迎探索Adventure")
        } else supportFragmentManager.beginTransaction().replace(R.id.progressFragment, WelcomeFragment()).commit()
<<<<<<< HEAD
=======
    }

    private fun initImagePicker() {
        val imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = GlideImageLoaderHelper()   //设置图片加载器
        imagePicker.isShowCamera = true                      //显示拍照按钮
        imagePicker.isCrop = true                           //允许裁剪（单选才有效）
        imagePicker.isSaveRectangle = true                   //是否按矩形区域保存
        imagePicker.selectLimit = 1            //选中数量限制
        imagePicker.style = CropImageView.Style.RECTANGLE  //裁剪框的形状
        imagePicker.focusWidth = 800                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.focusHeight = 800                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        //imagePicker.outPutX = 1000                         //保存文件的宽度。单位像素
        //imagePicker.outPutY = 1000                         //保存文件的高度。单位像素
>>>>>>> 07c9524f5977b3d98e481c08f7ea7e5c9196886f
    }
}
