package com.example.mvpsample.details

import com.example.mvpsample.model.FavoritesRepository
import com.example.mvpsample.model.database.FavoriteItem
import com.example.mvpsample.model.database.FavoritesDatabase
import com.example.mvpsample.model.networking.Item

class DetailsFragmentPresenter(var view: DetailsFragmentContract.View) :
    DetailsFragmentContract.Presenter {

    private val repository =
        FavoritesRepository(FavoritesDatabase.getInstance(view.getContext()).favoritesDatabaseDao)

    override fun clickOnHeart(item: Item?) {
        val favoriteItem =
            FavoriteItem(item!!.id, item.image, item.location, item.price, item.description)
        if (item.isFavorite) {
            item.isFavorite = false
            repository.delete(favoriteItem).subscribe()
        } else {
            repository.insert(favoriteItem).subscribe()
            item.isFavorite = true
        }
    }

    override fun isFavorite(item: Item?): Boolean {
        if (item?.isFavorite!!) {
            return true
        }
        return false
    }

}