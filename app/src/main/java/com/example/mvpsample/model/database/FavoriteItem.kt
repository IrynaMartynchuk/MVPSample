package com.example.mvpsample.model.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mvpsample.model.networking.Image
import com.example.mvpsample.model.networking.Price

//Creating a table
@Entity(tableName = "favorites")
data class FavoriteItem (

    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @Embedded val url: Image?,
    @ColumnInfo(name = "location") val location: String?,
    @Embedded val price: Price?,
    @ColumnInfo(name = "description") val description: String?)