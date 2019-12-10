package com.tongjisse.adventure.view.common

import android.support.v4.app.Fragment
import com.tongjisse.adventure.presenter.Presenter

abstract class BaseFragmentWithPresenter : Fragment() {
    abstract val presenter: Presenter
    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }
}