package com.tec9ers.thunderstorm.data

import com.tec9ers.thunderstorm.data.service.ApiService
import com.tec9ers.thunderstorm.model.CurrentWeatherResponse
import com.tec9ers.thunderstorm.model.Forecast
import com.tec9ers.thunderstorm.model.ForecastResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class Repository @Inject constructor (private val apiService : ApiService) {

    fun getCurrentWeatherSingle(queryParams : QueryParams) : Single<CurrentWeatherResponse> {
        return apiService.getCurrentWeather(queryParams.Params)
    }

    fun getForecastSingle(queryParams : QueryParams) : Single<ForecastResponse> {
        return apiService.getForecast(queryParams.Params)
    }
}