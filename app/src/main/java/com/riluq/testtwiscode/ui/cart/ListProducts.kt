package com.riluq.testtwiscode.ui.cart

import android.os.Parcelable
import com.riluq.testtwiscode.data.remote.response.SearchResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListProducts (
    val product: MutableList<SearchResponse>
): Parcelable
