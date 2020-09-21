package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.SerializedName

data class Forecast (

    @SerializedName("coord")
    var coord: Coord?,

    @SerializedName("weather")
    var weather: List<Weather>?,

    @SerializedName("main")
    var main: ForecastMain?,

    @SerializedName("visibility")
    var visibility: Long,

    @SerializedName("wind")
    var wind: Wind?,

    @SerializedName("clouds")
    var clouds: Clouds?,

    @SerializedName("dt")
    var dt: Long,

    @SerializedName("sys")
    var sys: ForecastSys?,


    @SerializedName("pop")
    val pop: Double ,

    @SerializedName("dt_text")
    val datetxt: String?
)
