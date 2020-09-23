package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse (
    
    @SerializedName("coord") val coord : Coord?,
    @SerializedName("weather") val weather : List<Weather>?,
    @SerializedName("base") val base : String?,
    @SerializedName("main") val main : CurrentWeatherMain?,
    @SerializedName("visibility") val visibility : Long,
    @SerializedName("wind") val wind : Wind?,
    @SerializedName("clouds") val clouds : Clouds?,
    @SerializedName("dt") val dt : Long,
    @SerializedName("sys") val sys : CurrentWeatherSys?,
    @SerializedName("id") val id : Long,
    @SerializedName("name") val name : String?,
    @SerializedName("cod") val cod : Long,
    @SerializedName("timezone") val timezone : Long
)