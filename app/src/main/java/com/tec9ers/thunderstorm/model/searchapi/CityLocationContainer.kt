package com.tec9ers.thunderstorm.model.searchapi

import com.google.gson.annotations.SerializedName

data class CityLocationContainer(

    @SerializedName("full_name")
    val full_name: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String
)
