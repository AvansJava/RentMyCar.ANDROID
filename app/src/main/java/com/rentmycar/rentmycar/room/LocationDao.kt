package com.rentmycar.rentmycar.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {

    @Query(" SELECT * FROM Location WHERE id = :key ")
    suspend fun getLocation(key: Int): Location

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createLocation(item: Location): Long

    @Query(" DELETE FROM Location ")
    suspend fun clear()

    @Query(" DELETE FROM Location WHERE id = :key ")
    suspend fun deleteLocation(key: Int)
}