package com.example.mvpsample.main

class MainActivityContract {

    interface View {
        fun setFragment(fragment: BaseFragment)
    }

    interface Presenter {
        fun getFirstFragment()
    }
}