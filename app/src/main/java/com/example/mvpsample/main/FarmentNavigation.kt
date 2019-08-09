package com.example.mvpsample.main

interface FragmentNavigation {
    interface View{
        fun attachPresenter(presenter: Presenter)
    }

    interface Presenter {
        fun addFragment (fragment: BaseFragment, previousFragment: BaseFragment?)
    }
}

