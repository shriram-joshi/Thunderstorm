package com.tec9ers.thunderstorm.model.searchapi

import com.google.gson.annotations.SerializedName

data class Embedded(
    @SerializedName("city:search-results")
    val cities: List<CityContainer>
)
