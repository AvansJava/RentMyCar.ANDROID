package com.rentmycar.rentmycar.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Location(

    @PrimaryKey(autoGenerate = false)
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "street")
    val street: String?,

    @Json(name = "houseNumber")
    val houseNumber: String?,

    @Json(name = "postalCode")
    val postalCode: String?,

    @Json(name = "city")
    val city: String?,

    @Json(name = "country")
    val country: String?,

    @Json(name = "latitude")
    val latitude: Double,

    @Json(name = "longitude")
    val longitude: Double
)
