package com.yuriysurzhikov.lab4.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class DataContact(
    var name: String?,
    var email: String?,
    var phone: String?,
    var imageProfile: Uri?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Uri::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeParcelable(imageProfile, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataContact> {
        override fun createFromParcel(parcel: Parcel): DataContact {
            return DataContact(parcel)
        }

        override fun newArray(size: Int): Array<DataContact?> {
            return arrayOfNulls(size)
        }
    }
}