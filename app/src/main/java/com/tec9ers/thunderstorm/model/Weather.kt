package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Weather (
    @SerializedName("id")
    var id: Long ,

    @SerializedName("main")
    var main: String? ,

    @SerializedName("description")
    var description: String? ,

    @SerializedName("icon")
    var icon: String?
)