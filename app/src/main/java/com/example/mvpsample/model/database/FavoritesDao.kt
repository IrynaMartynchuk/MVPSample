package com.example.mvpsample.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface FavoritesDao {

    @Query("select * from favorites")
    fun getAllFavorites(): Observable<List<FavoriteItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavorites(favorite: FavoriteItem): Completable

    @Query("delete from favorites where id = :id ")
    fun deleteFromFavorites(id: String): Completable
}