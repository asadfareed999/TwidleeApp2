package com.example.asadfareed.twidlee2.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.asadfareed.twidlee2.database.entity.DealRoom


@Dao
interface DealDao {
    @Insert(onConflict = REPLACE)
    fun save(deal: ArrayList<DealRoom>)

    @Query("SELECT * FROM DealRoom")
    fun load(): List<DealRoom>

    @Query("DELETE FROM DealRoom")
    fun clear()
}