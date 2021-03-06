package com.rentmycar.rentmycar.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Car(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "id")
    val id: Int?,

    @Json(name = "brand")
    val brand: String,

    @Json(name = "brandType")
    val brandType: String,

    @Json(name = "model")
    val model: String,

    @Json(name = "licensePlateNumber")
    val licensePlateNumber: String,

    @Json(name = "consumption")
    val consumption: Double,

    @Json(name = "costPrice")
    val costPrice: Int,

    @Json(name = "carType")
    val carType: String,

    @Json(name = "userId")
    val userId: Int?,

    @Json(name = "locationId")
    val locationId: Int? = null,
)
