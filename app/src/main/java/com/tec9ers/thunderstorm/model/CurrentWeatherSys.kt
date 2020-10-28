package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherSys(

    @SerializedName("type")
    val type: Long,

    @SerializedName("id")
    val id: Long,

    @SerializedName("message")
    val message: Float,

    @SerializedName("country")
    val country: String,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
)
