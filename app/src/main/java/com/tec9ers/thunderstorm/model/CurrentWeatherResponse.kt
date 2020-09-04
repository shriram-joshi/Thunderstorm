package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.SerializedName
import com.tec9ers.thunderstorm.CurrentWeatherMain


data class CurrentWeatherResponse (
    @SerializedName("coord")
    var coord: Coord?,

    @SerializedName("weather")
    var weather: List<Weather>?,

    @SerializedName("base")
    var base: String?,

    @SerializedName("main")
    var main: CurrentWeatherMain?,

    @SerializedName("visibility")
    var visibility: Long,

    @SerializedName("wind")
    var wind: Wind?,

    @SerializedName("clouds")
    var clouds: Clouds?,

    @SerializedName("dt")
    var dt: Long,

    @SerializedName("sys")
    var sys: CurrentWeatherSys?,

    @SerializedName("id")
    var id: Long,

    @SerializedName("name")
    var name: String?,

    @SerializedName("cod")
    var cod: Long = 0

)