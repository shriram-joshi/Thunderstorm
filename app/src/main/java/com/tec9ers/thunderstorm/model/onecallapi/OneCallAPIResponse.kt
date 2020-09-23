package com.tec9ers.thunderstorm.model.onecallapi

import com.google.gson.annotations.SerializedName

data class OneCallAPIResponse (

	@SerializedName("lat") val lat : Double,
	@SerializedName("lon") val lon : Double,
	@SerializedName("timezone") val timezone : String,
	@SerializedName("timezone_offset") val timezoneOffset : Int,
	@SerializedName("current") val current : Current,
	@SerializedName("minutely") val minutely : List<Minutely>,
	@SerializedName("hourly") val hourly : List<Hourly>,
	@SerializedName("daily") val daily : List<Daily>
)