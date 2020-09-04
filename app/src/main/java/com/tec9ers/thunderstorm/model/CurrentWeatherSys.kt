package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentWeatherSys (
    @SerializedName("type")
    var type: Long ,

    @SerializedName("id")
    var id: Long ,

    @SerializedName("message")
    var message: Float ,

    @SerializedName("country")
    var country: String? ,

    @SerializedName("sunrise")
    var sunrise: Long ,

    @SerializedName("sunset")
    var sunset: Long
)