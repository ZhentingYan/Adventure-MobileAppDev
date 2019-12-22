package com.tongjisse.adventure

import android.app.Application
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.view.CropImageView
import com.tongjisse.adventure.receiver.MyIntentService
import com.tongjisse.adventure.receiver.MyPushService
import com.tongjisse.adventure.utils.GlideImageLoaderHelper
import com.tongjisse.adventure.utils.OrmLiteHelper


/**
 * Init tools
 *
 * @author Feifan Wang
 */
class AdventureApp : Application() {
    override fun onCreate() {
        super.onCreate()
        OrmLiteHelper.createInstance(this)
        initImagePicker()
        com.igexin.sdk.PushManager.getInstance().initialize(applicationContext, MyPushService::class.java)
        com.igexin.sdk.PushManager.getInstance().registerPushIntentService(applicationContext, MyIntentService::class.java)
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
    }

}