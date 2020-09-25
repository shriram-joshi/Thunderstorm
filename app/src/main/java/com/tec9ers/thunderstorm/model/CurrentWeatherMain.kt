package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherMain (

    @SerializedName("temp")
    val temp : Float,

    @SerializedName("pressure")
    val pressure : Long,

    @SerializedName("humidity")
    val humidity : Long,

    @SerializedName("temp_min")
    val tempMin : Float,

    @SerializedName("temp_max")
    val tempMax : Float
)