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
        const val HOME = "HOME"
    }
}