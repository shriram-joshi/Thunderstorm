package com.tec9ers.thunderstorm.model.searchapi

import com.google.gson.annotations.SerializedName

data class CityLink(
    @SerializedName("href")
    val hyperLink: String
)
