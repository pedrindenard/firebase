package com.pdm.firebase.feature.domain.local.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImagesConverter {

    companion object {

        @JvmStatic
        @TypeConverter
        fun fromJsonToList(json: String?): List<String?>? {
            val collection = object : TypeToken<List<String?>?>() {}.type
            return Gson().fromJson(json, collection)
        }

        @JvmStatic
        @TypeConverter
        fun fromListToJson(list: List<String?>?): String? {
            val gson = Gson()
            return gson.toJson(list)
        }
    }
}