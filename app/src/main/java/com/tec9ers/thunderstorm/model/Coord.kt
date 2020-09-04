package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coord (
    @SerializedName("lon")
    val lon: Float ,

    @SerializedName("lat")
    val lat: Float
)