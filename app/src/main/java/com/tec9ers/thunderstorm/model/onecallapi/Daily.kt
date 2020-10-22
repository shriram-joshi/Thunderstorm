package com.tec9ers.thunderstorm.model.onecallapi

import com.google.gson.annotations.SerializedName
import com.tec9ers.thunderstorm.model.Weather

data class Daily (

	@SerializedName("dt")
	val dt : Long,

	@SerializedName("sunrise")
	val sunrise : Long,

	@SerializedName("sunset")
	val sunset : Long,

	@SerializedName("temp")
	val temp : Temp,

	@SerializedName("feels_like")
	val feelsLike : FeelsLike,

	@SerializedName("pressure")
	val pressure : Int,

	@SerializedName("humidity")
	val humidity : Int,

	@SerializedName("dew_point")
	val dewPoint : Double,

	@SerializedName("wind_speed")
	val windSpeed : Double,

	@SerializedName("wind_deg")
	val windDeg : Int,

	@SerializedName("weather")
	val weather : List<Weather>,

	@SerializedName("clouds")
	val clouds : Int,

	@SerializedName("pop")
	val pop : Double,

	@SerializedName("rain")
	val rain : Double,

	@SerializedName("uvi")
	val uvi : Double
)