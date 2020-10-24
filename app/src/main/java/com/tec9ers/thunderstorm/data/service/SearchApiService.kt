package com.tec9ers.thunderstorm.data.service

import com.tec9ers.thunderstorm.model.searchapi.SearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("cities")
    fun getSuggestedCities(@Query("search") query: String): Single<SearchResponse>
}