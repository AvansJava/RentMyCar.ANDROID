package com.rentmycar.rentmycar.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CarDao {

    @Query(" SELECT * FROM Car WHERE id = :key ")
    suspend fun getCar(key: Int): Car

    @Insert
    suspend fun createCar(item: Car): Long

    @Query(" DELETE FROM Car WHERE id = :key ")
    suspend fun deleteCar(key: Int)

    @Query(" UPDATE car SET locationId = :key WHERE id = :id ")
    suspend fun updateCar(key: Int, id: Int)

    @Query(" DELETE FROM Car ")
    suspend fun clear()
}