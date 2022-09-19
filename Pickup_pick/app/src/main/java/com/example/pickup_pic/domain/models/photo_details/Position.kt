package com.example.pickup_pic.domain.models.photo_details

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Position(
    @Json(name = "latitude") val latitude: Double?,
    @Json(name = "longitude") val longitude: Double?
)