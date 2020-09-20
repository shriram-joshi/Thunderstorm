package com.tec9ers.thunderstorm.data.service

import com.tec9ers.thunderstorm.model.CurrentWeatherResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{
    @GET("weather")
    fun getCurrentWeather(@Query("q") city: String): Single<CurrentWeatherResponse>

    @GET("weather")
    fun getCurrentWeather(@Query("id") city: Int): Single<CurrentWeatherResponse>

    @GET("weather")
    fun getCurrentWeather(@Query("lat") city: Double,@Query("longitude")longitude: Double): Single<CurrentWeatherResponse>
}