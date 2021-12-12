package com.rentmycar.rentmycar.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CarDao {

    @Query(" SELECT * FROM Car WHERE id = :key ")
    suspend fun getCar(key: Int): Car

    @Insert
    suspend fun createCar(item: Car)

    @Query(" delete from Car WHERE id = :key ")
    suspend fun deleteCar(key: Int)
}