package com.tongjisse.adventure.view.views.Image

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

import com.tongjisse.adventure.R

/**
 * Select dialog
 *
 * @author Feifan Wang
 */

class SelectDialog : Dialog, OnClickListener, OnItemClickListener {
    private var mListener: SelectDialogListener? = null
    private var mActivity: Activity? = null
    private var mMBtn_Cancel: Button? = null
    private var mTv_Title: TextView? = null
    private var mName: List<String>? = null
    private var mTitle: String? = null
    private var mUseCustomColor = false
    private var mFirstItemColor: Int = 0
    private var mOtherItemColor: Int = 0



    // 取消事件监听接口
    private var mCancelListener: SelectDialogCancelListener? = null

    interface SelectDialogListener {
        fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long)
    }

    interface SelectDialogCancelListener {
        fun onCancelClick(v: View)
    }

    constructor(activity: Activity, theme: Int,
                listener: SelectDialogListener, names: List<String>) : super(activity, theme) {
        mActivity = activity
        mListener = listener
        this.mName = names

        setCanceledOnTouchOutside(true)
    }

    constructor(activity: Activity, theme: Int, listener: SelectDialogListener, cancelListener: SelectDialogCancelListener, names: List<String>) : super(activity, theme) {
        mActivity = activity
        mListener = listener
        mCancelListener = cancelListener
        this.mName = names

        // 设置是否点击外围不解散
        setCanceledOnTouchOutside(false)
    }

    constructor(activity: Activity, theme: Int, listener: SelectDialogListener, names: List<String>, title: String) : super(activity, theme) {
        mActivity = activity
        mListener = listener
        this.mName = names
        mTitle = title

        // 设置是否点击外围可解散
        setCanceledOnTouchOutside(true)
    }

    constructor(activity: Activity, theme: Int, listener: SelectDialogListener, cancelListener: SelectDialogCancelListener, names: List<String>, title: String) : super(activity, theme) {
        mActivity = activity
        mListener = listener
        mCancelListener = cancelListener
        this.mName = names
        mTitle = title

        // 设置是否点击外围可解散
        setCanceledOnTouchOutside(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.view_dialog_select, null)
        setContentView(view, LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT))
        val window = window
        // 设置显示动画
        window!!.setWindowAnimations(R.style.main_menu_animstyle)
        val wl = window.attributes
        wl.x = 0
        wl.y = mActivity!!.windowManager.defaultDisplay.height
        // 保证按钮可以水平满屏
        wl.width = LayoutParams.MATCH_PARENT
        wl.height = LayoutParams.WRAP_CONTENT

        // 设置显示位置
        onWindowAttributesChanged(wl)

        initViews()
    }

    private fun initViews() {
        val dialogAdapter = DialogAdapter(mName!!)
        val dialogList = findViewById<View>(R.id.dialog_list) as ListView
        dialogList.onItemClickListener = this
        dialogList.adapter = dialogAdapter
        mMBtn_Cancel = findViewById<View>(R.id.mBtn_Cancel) as Button
        mTv_Title = findViewById<View>(R.id.mTv_Title) as TextView


        mMBtn_Cancel!!.setOnClickListener { v ->
            if (mCancelListener != null) {
                mCancelListener!!.onCancelClick(v)
            }
            dismiss()
        }

        if (!TextUtils.isEmpty(mTitle) && mTv_Title != null) {
            mTv_Title!!.visibility = View.VISIBLE
            mTv_Title!!.text = mTitle
        } else {
            mTv_Title!!.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        dismiss()

    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int,
                             id: Long) {

        mListener!!.onItemClick(parent, view, position, id)
        dismiss()
    }

    private inner class DialogAdapter(private val mStrings: List<String>) : BaseAdapter() {
        private var viewholder: Viewholder? = null
        private val layoutInflater: LayoutInflater

        init {
            this.layoutInflater = mActivity!!.layoutInflater
        }

        override fun getCount(): Int {
            return mStrings.size
        }

        override fun getItem(position: Int): Any {
            return mStrings[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            if (null == convertView) {
                viewholder = Viewholder()
                convertView = layoutInflater.inflate(R.layout.view_dialog_item, null)
                viewholder!!.dialogItemButton = convertView!!.findViewById<View>(R.id.dialog_item_bt) as TextView
                convertView.tag = viewholder
            } else {
                viewholder = convertView.tag as Viewholder
            }
            viewholder!!.dialogItemButton!!.text = mStrings[position]
            if (!mUseCustomColor) {
                mFirstItemColor = mActivity!!.resources.getColor(R.color.blue)
                mOtherItemColor = mActivity!!.resources.getColor(R.color.blue)
            }
            if (1 == mStrings.size) {
                viewholder!!.dialogItemButton!!.setTextColor(mFirstItemColor)
                viewholder!!.dialogItemButton!!.setBackgroundResource(R.drawable.dialog_item_bg_only)
            } else if (position == 0) {
                viewholder!!.dialogItemButton!!.setTextColor(mFirstItemColor)
                viewholder!!.dialogItemButton!!.setBackgroundResource(R.drawable.select_dialog_item_bg_top)
            } else if (position == mStrings.size - 1) {
                viewholder!!.dialogItemButton!!.setTextColor(mOtherItemColor)
                viewholder!!.dialogItemButton!!.setBackgroundResource(R.drawable.select_dialog_item_bg_buttom)
            } else {
                viewholder!!.dialogItemButton!!.setTextColor(mOtherItemColor)
                viewholder!!.dialogItemButton!!.setBackgroundResource(R.drawable.select_dialog_item_bg_center)
            }
            return convertView
        }

    }

    class Viewholder {
        var dialogItemButton: TextView? = null
    }

    /**
     * 设置列表项的文本颜色
     *
     * @param firstItemColor: Int
     * @param otherItemColor: Int
     */
    fun setItemColor(firstItemColor: Int, otherItemColor: Int) {
        mFirstItemColor = firstItemColor
        mOtherItemColor = otherItemColor
        mUseCustomColor = true
    }
}
