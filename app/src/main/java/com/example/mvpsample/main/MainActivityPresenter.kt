package com.example.mvpsample.main

import com.example.mvpsample.overview.OverviewFragment

class MainActivityPresenter(_view: MainActivityContract.View) : MainActivityContract.Presenter,
    FragmentNavigation.Presenter {
    private var view: MainActivityContract.View = _view

    override fun getFirstFragment() {
        view.setFragment(OverviewFragment())

    }

    override fun addFragment(fragment: BaseFragment, previousFragment: BaseFragment?) {
        view.setFragment(fragment, previousFragment)
    }

}