package com.tec9ers.thunderstorm.data.service

import com.tec9ers.thunderstorm.model.searchapi.SearchResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchRepository @Inject constructor(val searchApiService: SearchApiService) {
    fun getSuggestionsSingle(query: String): Single<SearchResponse> {
        return searchApiService.getSuggestedCities(query)
    }
}