package com.tec9ers.thunderstorm.data.service

import com.tec9ers.thunderstorm.model.CurrentWeatherResponse
import com.tec9ers.thunderstorm.model.ForecastResponse
import com.tec9ers.thunderstorm.model.onecallapi.OneCallAPIResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("weather")
    fun getCurrentWeather(@QueryMap map: HashMap<String, String>): Single<CurrentWeatherResponse>

    @GET("forecast")
    fun getForecast(@QueryMap map: HashMap<String, String>): Single<ForecastResponse>

    @GET("onecall")
    fun getOneCallApiData(@QueryMap map: HashMap<String, String>): Single<OneCallAPIResponse>
}
