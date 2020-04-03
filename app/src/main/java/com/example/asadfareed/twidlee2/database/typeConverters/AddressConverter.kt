package com.example.asadfareed.twidlee2.database.typeConverters

import androidx.room.TypeConverter
import com.example.asadfareed.twidlee2.database.entity.Address
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddressConverter {
    @TypeConverter
    fun toAdress(json: String): Address {
        val type = object : TypeToken<Address>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(address: Address): String {
        val type = object: TypeToken<Address>() {}.type
        return Gson().toJson(address, type)
    }
}