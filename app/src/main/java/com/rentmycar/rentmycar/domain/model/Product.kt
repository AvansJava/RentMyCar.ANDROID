package com.rentmycar.rentmycar.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Product(
    val id: Int,
    val reservationNumber: String?,
    val price: Double,
    val rentalPlanId: Int,
    val insuranceTypeId: String?,
    val insurancePrice: Double,
    val status: String?,
    val createdAt: String?,
    val updatedAt: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(reservationNumber)
        parcel.writeDouble(price)
        parcel.writeInt(rentalPlanId)
        parcel.writeString(insuranceTypeId)
        parcel.writeDouble(insurancePrice)
        parcel.writeString(status)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
