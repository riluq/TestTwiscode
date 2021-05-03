package com.riluq.testtwiscode.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse (
    @Json(name = "status")
    val status: Boolean?,
    @Json(name = "error")
    val error: String?
)