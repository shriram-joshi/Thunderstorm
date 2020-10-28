package com.tec9ers.thunderstorm.data

import com.tec9ers.thunderstorm.data.service.ApiService
import com.tec9ers.thunderstorm.model.CurrentWeatherResponse
import com.tec9ers.thunderstorm.model.ForecastResponse
import com.tec9ers.thunderstorm.model.onecallapi.OneCallAPIResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    fun getCurrentWeatherSingle(queryParams: QueryParams): Single<CurrentWeatherResponse> {
        return apiService.getCurrentWeather(queryParams.params)
    }

    fun getForecastSingle(queryParams: QueryParams): Single<ForecastResponse> {
        return apiService.getForecast(queryParams.params)
    }

    fun getOneCallApiDataSingle(queryParams: QueryParams): Single<OneCallAPIResponse> {
        return apiService.getOneCallApiData(queryParams.params)
    }
}
