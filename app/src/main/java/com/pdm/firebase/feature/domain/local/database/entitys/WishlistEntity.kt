package com.pdm.firebase.feature.domain.local.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.pdm.firebase.feature.domain.local.database.converters.CollectionConverter
import com.pdm.firebase.feature.domain.local.database.converters.ImagesConverter
import com.pdm.firebase.feature.domain.model.MyList

@Entity(tableName = "wishlist")
data class WishlistEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wishlist_id") val id: Int = 0,

    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "description") val description: String = "",

    @TypeConverters(ImagesConverter::class)
    @ColumnInfo(name = "image") val images: List<String> = listOf(),

    @TypeConverters(CollectionConverter::class)
    @ColumnInfo(name = "collection") val myList: List<MyList> = listOf()
)