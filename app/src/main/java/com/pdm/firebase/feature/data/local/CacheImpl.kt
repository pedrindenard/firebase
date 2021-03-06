package com.pdm.firebase.feature.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.pdm.firebase.feature.domain.local.Cache
import kotlinx.coroutines.flow.first

class CacheImpl(private val context: Context) : Cache {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override suspend fun insert(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    override suspend fun get(key: String): String {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[dataStoreKey] ?: ""
    }

    override suspend fun delete(key: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings.remove(dataStoreKey)
        }
    }

    override suspend fun clear() {
        context.dataStore.edit { settings ->
            settings.clear()
        }
    }

    companion object {
        const val DEFAULT = "DEFAULT"
        const val REGIONS = "REGIONS"
        const val POPULAR_MOVIE = "POPULAR_MOVIE"
        const val RATED_MOVIE = "RATED_MOVIE"
        const val GENDERS_MOVIE = "GENDER_MOVIE"
        const val GENDERS_TV = "GENDER_TV"
        const val NOW_PLAYING_MOVIE = "NOW_PLAYING_MOVIE"
        const val POPULAR_TV = "POPULAR_TV"
        const val TOP_RATED_TV = "TOP_RATED_TV"
        const val MOVIE_BY_GENDER = "MOVIE_BY_GENDER"
        const val TV_BY_GENDER = "TV_BY_GENDER"
        const val UPCOMING_MOVIE = "UPCOMING_MOVIE"
        const val BEST_ACTORS = "BEST_ACTORS"
        const val ON_AIR_TV = "ON_AIR_TV"
    }
}