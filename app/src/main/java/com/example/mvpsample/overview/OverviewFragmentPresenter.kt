package com.example.mvpsample.overview

import android.util.Log
import com.example.mvpsample.di.DaggerPresenterComponent
import com.example.mvpsample.di.NetworkModule
import com.example.mvpsample.model.FavoritesRepository
import com.example.mvpsample.model.database.FavoriteItem
import com.example.mvpsample.model.database.FavoritesDatabase
import com.example.mvpsample.model.networking.ApiService
import com.example.mvpsample.model.networking.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OverviewFragmentPresenter(val view: OverviewFragmentContract.View) :
    OverviewFragmentContract.Presenter {

    @Inject
    lateinit var api: ApiService
    private var items: List<Item>? = arrayListOf()
    private var repository =
        FavoritesRepository(FavoritesDatabase.getInstance(view.getContext()).favoritesDatabaseDao)
    private var disposable: Disposable? = null
    private var listOfFavorites: List<Item> = ArrayList()

    init {

        DaggerPresenterComponent.builder()
            .networkModule(NetworkModule())
            .build()
            .inject(this)

        disposable = repository.getFavorites()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { favorites ->
                    listOfFavorites = favorites.map {
                        Item(
                            id = it.id,
                            image = it.url,
                            location = it.location,
                            price = it.price,
                            description = it.description,
                            isFavorite = true
                        )
                    }
                    if (view.getToggleStatus()) {
                        view.onGetSuccessResult(listOfFavorites)
                    }
                },
                { error -> Log.e("Error", error.message!!) })
    }

    override fun showFavorites() {
        listOfFavorites.forEach {
            it.isFavorite = true
        }
        view.onGetSuccessResult(listOfFavorites)

    }


    override fun hideFavorites() {
        getData()
    }

    override fun clickOnHeart(item: Item) {
        val newFavorite =
            FavoriteItem(item.id, item.image, item.location, item.price, item.description)
        if (item.isFavorite) {
            item.isFavorite = false
            repository.delete(newFavorite).subscribe()

        } else {
            item.isFavorite = true
            repository.insert(newFavorite).subscribe()
        }
    }

    override fun markFavorites() {
        listOfFavorites.forEach {
            it.isFavorite = false
        }

        items?.forEach {
            if (listOfFavorites.contains(it)) {
                it.isFavorite = true
            }
        }
    }

    override fun getData(): Disposable {

        return api.getData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                items = response.items
                markFavorites()
                view.onGetSuccessResult(items)

            }, {
                if (listOfFavorites.isNotEmpty()) {
                    items = listOfFavorites
                    markFavorites()
                    view.onGetSuccessResult(items)
                } else view.connectionError()
            })
    }

    override fun loadData(isToggled: Boolean) {
        if (isToggled) {
            showFavorites()
        } else getData()
    }

    override fun clear() {
        disposable?.dispose()
    }
}