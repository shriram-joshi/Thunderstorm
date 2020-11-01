package com.tec9ers.thunderstorm.data.service

import com.tec9ers.thunderstorm.model.searchapi.CityLocationContainer
import com.tec9ers.thunderstorm.model.searchapi.SearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface SearchApiService {
    @GET("cities")
    fun getSuggestedCities(@Query("search") query: String): Single<SearchResponse>

    @GET
    fun getCityLocation(@Url url: String): Single<CityLocationContainer>
}
