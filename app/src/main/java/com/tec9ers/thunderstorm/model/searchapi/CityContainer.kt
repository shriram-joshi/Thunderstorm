package com.tec9ers.thunderstorm.model.searchapi

import com.google.gson.annotations.SerializedName

data class CityContainer(
    @SerializedName("matching_full_name")
    val fullName: String,
    @SerializedName("_links")
    val links: CityLink
)