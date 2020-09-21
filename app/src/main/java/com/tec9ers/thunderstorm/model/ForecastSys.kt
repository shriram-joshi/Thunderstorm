package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.SerializedName

data class ForecastSys(
    @SerializedName("pod")
    val pod : Char?
)