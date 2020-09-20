package com.tec9ers.thunderstorm.data

import com.tec9ers.thunderstorm.data.service.ApiService
import com.tec9ers.thunderstorm.model.CurrentWeatherResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class Repository @Inject constructor (private val apiService: ApiService){

    fun getCurrentWeatherSingle(city: String): Single<CurrentWeatherResponse> {
        return apiService.getCurrentWeather(city)
    }

    fun getCurrentWeatherSingle(lat: Double, longitude: Double): Single<CurrentWeatherResponse> {
        return apiService.getCurrentWeather(lat,longitude)
    }

    fun getCurrentWeatherSingle(city: Int): Single<CurrentWeatherResponse> {
        return apiService.getCurrentWeather(city)
    }
}