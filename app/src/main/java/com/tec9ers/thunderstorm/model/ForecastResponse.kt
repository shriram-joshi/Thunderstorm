package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(

    @SerializedName("cod")
    val cod: Int,

    @SerializedName("message")
    val message: Int,

    @SerializedName("cnt")
    val cnt: Int,

    @SerializedName("city")
    val city: City,

    @SerializedName("list")
    val forecastList: List<Forecast>
)
