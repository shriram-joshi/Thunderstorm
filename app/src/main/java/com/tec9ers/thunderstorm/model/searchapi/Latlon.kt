package com.tec9ers.thunderstorm.model.searchapi

import com.google.gson.annotations.SerializedName

data class Latlon(

    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)
