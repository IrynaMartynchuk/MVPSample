package com.example.mvpsample.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvpsample.R

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            presenter = MainActivityPresenter(this)
            presenter.getFirstFragment()
        }

    }

    override fun setFragment(fragment: BaseFragment, previousFragment: BaseFragment?) {
        fragment.attachPresenter(presenter)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .apply {
                previousFragment?.let {
                    addToBackStack(it.toString())
                }
            }
            .commit()
    }
}
