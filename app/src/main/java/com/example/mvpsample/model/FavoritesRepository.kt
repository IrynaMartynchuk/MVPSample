package com.example.mvpsample.model

import androidx.annotation.WorkerThread
import com.example.mvpsample.model.database.FavoriteItem
import com.example.mvpsample.model.database.FavoritesDao
import io.reactivex.Completable
import io.reactivex.Observable

class FavoritesRepository(private val favoritesDao: FavoritesDao) {

    fun getFavorites(): Observable<List<FavoriteItem>> {
        return favoritesDao.getAllFavorites()
    }

    fun insert(favorite: FavoriteItem): Completable {
        return favoritesDao.addToFavorites(favorite)
    }

    @WorkerThread
    fun delete(favorite: FavoriteItem): Completable {
        return favoritesDao.deleteFromFavorites(favorite.id)
    }
}