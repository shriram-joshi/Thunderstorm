package com.tec9ers.thunderstorm.model.searchapi

import com.google.gson.annotations.SerializedName

data class CityLink(
    @SerializedName("city:item")
    val cityItem: CityItem
)
