package com.riluq.testtwiscode.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class DefaultPhoto (
    @Json(name = "img_path")
    val imgPath: String?
): Parcelable