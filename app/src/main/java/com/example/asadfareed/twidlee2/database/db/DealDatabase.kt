package com.example.asadfareed.twidlee2.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.asadfareed.twidlee2.database.entity.DealRoom
import com.example.asadfareed.twidlee2.database.dao.DealDao
import com.example.asadfareed.twidlee2.database.typeConverters.AddressConverter
import com.example.asadfareed.twidlee2.database.typeConverters.CuisineConverter

@Database(entities = [DealRoom::class], version = 1, exportSchema = false)
@TypeConverters(
    AddressConverter::class,
    CuisineConverter::class)
abstract class DealDatabase : RoomDatabase() {
    abstract fun dealDao(): DealDao

    companion object {
        private var instance: DealDatabase? = null
        fun getInstance( context: Context): DealDatabase? {
            if (instance == null) {
                synchronized(DealDatabase::class.java) {
                    instance = Room.databaseBuilder(context.applicationContext, DealDatabase::class.java, "deals_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}