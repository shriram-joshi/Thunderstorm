package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Wind (
    @SerializedName("speed")
    var speed: Float ,

    @SerializedName("deg")
    var deg: Long
)