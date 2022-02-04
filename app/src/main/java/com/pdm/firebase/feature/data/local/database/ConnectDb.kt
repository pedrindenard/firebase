package com.pdm.firebase.feature.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pdm.firebase.feature.data.local.database.dao.AddressDao
import com.pdm.firebase.feature.data.local.database.dao.ProfileDao
import com.pdm.firebase.feature.data.local.database.dao.WishlistDao
import com.pdm.firebase.feature.domain.local.database.converters.CollectionConverter
import com.pdm.firebase.feature.domain.local.database.converters.ImagesConverter
import com.pdm.firebase.feature.domain.local.database.entitys.AddressEntity
import com.pdm.firebase.feature.domain.local.database.entitys.ProfileEntity
import com.pdm.firebase.feature.domain.local.database.entitys.WishlistEntity
import com.pdm.firebase.util.DATABASE_APP

@Database(
    entities = [
        ProfileEntity::class,
        AddressEntity::class,
        WishlistEntity::class
    ],
    version = 1, exportSchema = false
)

@TypeConverters(
    CollectionConverter::class,
    ImagesConverter::class
)

abstract class ConnectDb : RoomDatabase() {

    abstract fun profileDao(): ProfileDao
    abstract fun addressDao(): AddressDao
    abstract fun wishlistDao(): WishlistDao

    companion object {

        @Volatile
        private var INSTANCE: ConnectDb? = null

        fun getInstance(context: Context): ConnectDb {
            return if (INSTANCE != null) INSTANCE!! else {
                synchronized(lock = this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ConnectDb::class.java,
                        DATABASE_APP
                    ).build()

                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}