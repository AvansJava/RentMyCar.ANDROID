package com.rentmycar.rentmycar.network.request

import android.os.Parcel
import android.os.Parcelable

data class TimeslotIdRequest(
    val id: Int
): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TimeslotIdRequest> {
        override fun createFromParcel(parcel: Parcel): TimeslotIdRequest {
            return TimeslotIdRequest(parcel)
        }

        override fun newArray(size: Int): Array<TimeslotIdRequest?> {
            return arrayOfNulls(size)
        }
    }
}
