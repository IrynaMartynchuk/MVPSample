package com.example.mvpsample.overview

import android.content.Context
import com.example.mvpsample.model.networking.Item
import io.reactivex.disposables.Disposable

interface OverviewFragmentContract {

    interface View {
        fun onGetSuccessResult(items: List<Item>?)
        fun getContext(): Context
        fun connectionError()
        fun getToggleStatus(): Boolean
    }

    interface Presenter {
        fun getData(): Disposable
        fun showFavorites()
        fun hideFavorites()
        fun clickOnHeart(item: Item)
        fun markFavorites()
        fun loadData(isToggled: Boolean)
        fun clear()
    }
}