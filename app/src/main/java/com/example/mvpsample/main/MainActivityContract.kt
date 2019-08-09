package com.example.mvpsample.main

class MainActivityContract {

    interface View {
        fun setFragment(fragment: BaseFragment, previousFragment: BaseFragment? = null)
    }

    interface Presenter {
        fun getFirstFragment()
    }
}