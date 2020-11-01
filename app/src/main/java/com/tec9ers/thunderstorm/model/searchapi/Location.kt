package com.tec9ers.thunderstorm.model.searchapi

import com.google.gson.annotations.SerializedName

data class Location(

    @SerializedName("latlon")
    val latlon: Latlon
)
