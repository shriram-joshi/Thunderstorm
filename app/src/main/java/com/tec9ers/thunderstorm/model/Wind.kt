package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.SerializedName

data class Wind (
    @SerializedName("speed") val speed: Float,
    @SerializedName("deg") val deg : Long
)