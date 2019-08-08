package com.example.mvpsample.details

import android.content.Context
import com.example.mvpsample.model.networking.Item

interface DetailsFragmentContract {
    interface View {
        fun getContext(): Context
    }

    interface Presenter {
        fun isFavorite(item: Item?): Boolean
        fun clickOnHeart(item: Item?)

    }


}