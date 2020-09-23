package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.SerializedName

data class Forecast (

    @SerializedName("coord")
    val coord : Coord,

    @SerializedName("weather")
    val weather : List<Weather>,

    @SerializedName("main")
    val main : ForecastMain,

    @SerializedName("visibility")
    val visibility : Long,

    @SerializedName("wind")
    val wind : Wind,

    @SerializedName("clouds")
    val clouds : Clouds,

    @SerializedName("dt")
    val dt : Long,

    @SerializedName("sys")
    val sys : ForecastSys,

    @SerializedName("pop")
    val pop : Double,

    @SerializedName("dt_text")
    val datetxt : String
)
