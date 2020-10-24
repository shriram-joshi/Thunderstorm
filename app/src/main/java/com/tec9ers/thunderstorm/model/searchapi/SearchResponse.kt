package com.tec9ers.thunderstorm.model.searchapi

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("_embedded")
    val embedded: Embedded,
    val count: Int
)