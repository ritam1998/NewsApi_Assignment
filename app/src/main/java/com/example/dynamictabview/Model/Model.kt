package com.example.dynamictabview.Model

import android.os.Parcel
import android.os.Parcelable

class Model(
    var worldheadLine: String?,
    var worldNewsBody: String?,
    var worldNewsPostDate: String?,
    var worldImageView: String?,
    var nameOfJournal : String?,
    var newsContent : String?,
    var authorName : String?,
    var fullContentRead : String?,
    var isRead : Boolean) : Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(worldheadLine)
        parcel.writeString(worldNewsBody)
        parcel.writeString(worldNewsPostDate)
        parcel.writeString(worldImageView)
        parcel.writeString(nameOfJournal)
        parcel.writeString(newsContent)
        parcel.writeString(authorName)
        parcel.writeString(fullContentRead)
        parcel.writeByte(if (isRead) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Model> {
        override fun createFromParcel(parcel: Parcel): Model {
            return Model(parcel)
        }

        override fun newArray(size: Int): Array<Model?> {
            return arrayOfNulls(size)
        }
    }

}