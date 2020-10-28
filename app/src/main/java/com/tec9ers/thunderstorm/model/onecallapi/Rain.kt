package com.tec9ers.thunderstorm.model.onecallapi

import com.google.gson.annotations.SerializedName

data class Rain(

    @SerializedName("1h")
    val hour: Double
)
