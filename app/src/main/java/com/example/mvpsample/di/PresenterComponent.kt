package com.example.mvpsample.di

import com.example.mvpsample.overview.OverviewFragmentPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface PresenterComponent {
    fun inject(presenter: OverviewFragmentPresenter)
}