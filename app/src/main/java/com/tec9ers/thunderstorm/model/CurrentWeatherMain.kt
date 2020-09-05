package com.tec9ers.thunderstorm

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




 data class CurrentWeatherMain (
  @SerializedName("temp")
  var temp: Float ,

  @SerializedName("pressure")
  var pressure: Long ,

  @SerializedName("humidity")
  var humidity: Long ,

  @SerializedName("temp_min")
  var tempMin: Float ,

  @SerializedName("temp_max")
  var tempMax: Float

)