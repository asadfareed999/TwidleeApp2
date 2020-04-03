package com.example.asadfareed.twidlee2.database.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CuisineConverter {
    @TypeConverter
    fun toCuisines(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(cuisines: List<String>) = Gson().toJson(cuisines)
}