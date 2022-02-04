package com.pdm.firebase.feature.domain.local.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pdm.firebase.feature.domain.model.Collection

class CollectionConverter {

    companion object {

        @JvmStatic
        @TypeConverter
        fun fromJsonToList(json: String?): List<Collection?>? {
            val collection = object : TypeToken<List<Collection?>?>() {}.type
            return Gson().fromJson(json, collection)
        }

        @JvmStatic
        @TypeConverter
        fun fromListToJson(list: List<Collection?>?): String? {
            val gson = Gson()
            return gson.toJson(list)
        }
    }
}