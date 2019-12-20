package com.tongjisse.adventure.view.views.WishList

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.tongjisse.adventure.R
import com.tongjisse.adventure.data.bean.WishList
import com.tongjisse.adventure.presenter.WishList.WishListPresenter
import com.tongjisse.adventure.utils.CityPickerHelper
import com.tongjisse.adventure.utils.SessionManager
import com.tongjisse.adventure.view.common.BaseFragmentWithPresenter
import com.tongjisse.adventure.view.common.toast
import com.tongjisse.adventure.view.main.MainListAdapter
import com.tongjisse.adventure.view.main.WishList.WishListAdapter
import com.tongjisse.adventure.view.main.WishList.WishListView
import kotlinx.android.synthetic.main.fragment_wishlist.*
import java.sql.SQLException


class WishListFragment : BaseFragmentWithPresenter(), WishListView {
    lateinit var mJDCityPiker: CityPickerHelper
    lateinit var mSessionManager: SessionManager
    override val presenter by lazy { WishListPresenter(this) }

    override fun getUserWishListsSuccess(userWishList: List<WishList>) {
        //筛选不符合用户所在地的心愿列表
        if (userWishList != null) {

            if (userWishList.size != 0) {
                ErrorLayout.visibility = View.GONE
                wishRecyclerView.visibility = View.VISIBLE
                val categoryItemAdapters = userWishList.map(::WishListAdapter)
                wishRecyclerView.adapter = MainListAdapter(categoryItemAdapters)
            } else {
                ErrorLayout.visibility = View.VISIBLE
                wishRecyclerView.visibility = View.GONE
                tvError.text = "在${mSessionManager.district}你还没有心愿哦......"
            }
        } else {
            ErrorLayout.visibility = View.VISIBLE
            wishRecyclerView.visibility = View.GONE
            tvError.text = "在${mSessionManager.district}你还没有心愿哦......"
        }
    }

    override fun getUserWishListsFailed(error: SQLException) {
        ErrorLayout.visibility = View.VISIBLE
        wishRecyclerView.visibility = View.GONE
        tvError.text = "加载心愿单失败......"
        context!!.toast("加载心愿单失败，错误信息:${error.message}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        wishRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mSessionManager = SessionManager(context)
        mJDCityPiker = CityPickerHelper(context, object : OnCityItemClickListener() {
            override fun onSelected(province: ProvinceBean?, city: CityBean?, district: DistrictBean?) {
                mSessionManager.refineLocation(province!!.name, city!!.name, district!!.name, mSessionManager.longitude, mSessionManager.latitude)
                tvLocation.text = mSessionManager.defaultAddress
                presenter.showUserWishLists(mSessionManager.email, mSessionManager.district)
            }
        })

        swipeRefreshView.setOnRefreshListener {
            swipeRefreshView.isRefreshing = true
            //ScenicSpotFragment.locationClient!!.startLocation()
            presenter.showUserWishLists(mSessionManager.email, mSessionManager.district)
            swipeRefreshView.isRefreshing = false
        }
        fragmentInit()
        tvLocation.setOnClickListener {
            mJDCityPiker.showJD()
        }
    }

    fun fragmentInit() {
        if (mSessionManager.district.equals("")) {
            tvLocation.text = "请选择目的地"
            mJDCityPiker.showJD()
        } else {
            tvLocation.text = mSessionManager.defaultAddress
            presenter.showUserWishLists(mSessionManager.email, mSessionManager.district)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden)
            fragmentInit()
    }
}