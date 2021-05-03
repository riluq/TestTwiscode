package com.riluq.testtwiscode.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.riluq.testtwiscode.data.local.room.AppDatabase
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = AppDatabase.FAVORITE_TABLE_NAME)
class FavoriteEntity (

    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    var id: Int?,

    @field:ColumnInfo(name = "rsId")
    val rsId: Int?,

    @field:ColumnInfo(name = "doctorId")
    val doctorId: Int?,

    @field:ColumnInfo(name = "hospitalName")
    val hospitalName: String?,

    @field:ColumnInfo(name = "hospitalAddress")
    val hospitalAddress: String?,

    @field:ColumnInfo(name = "doctorName")
    val doctorName: String?,

    @field:ColumnInfo(name = "specialist")
    val specialist: String?,

    @field:ColumnInfo(name = "photo")
    val photo: String?
): Parcelable