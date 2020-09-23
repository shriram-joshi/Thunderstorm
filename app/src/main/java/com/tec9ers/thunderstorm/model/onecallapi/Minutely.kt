package com.tec9ers.thunderstorm.model.onecallapi

import com.google.gson.annotations.SerializedName

data class Minutely (

	@SerializedName("dt") val dt : Int,
	@SerializedName("precipitation") val precipitation : Int
)