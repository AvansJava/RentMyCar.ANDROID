package com.rentmycar.rentmycar.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Reservation(
    val reservationNumber: String?,
    val price: Double,
    val status: String?,
    val paidAt: String?,
    val product: Product?
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable<Product>(Product::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(reservationNumber)
        parcel.writeDouble(price)
        parcel.writeString(status)
        parcel.writeString(paidAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reservation> {
        override fun createFromParcel(parcel: Parcel): Reservation {
            return Reservation(parcel)
        }

        override fun newArray(size: Int): Array<Reservation?> {
            return arrayOfNulls(size)
        }
    }
}
