package com.riluq.testtwiscode.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SearchResponse (
    @Json(name = "id")
    val id: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "address")
    val address: String?,
    @Json(name = "location_name")
    val locationName: String?,
    @Json(name = "added_user_name")
    val addedUserName: String?,
    @Json(name = "price")
    val price: String?,
    @Json(name = "is_halal")
    val isHalal: String?,
    var index: Int = 0,
    var isAdd: Boolean = false,
    var total: Int = 1,
    @Json(name = "default_photo")
    val defaultPhoto: DefaultPhoto?
): Parcelable