package com.tec9ers.thunderstorm.model

import com.google.gson.annotations.SerializedName

data class ForecastMain(
    @SerializedName("temp")
    var temp: Float ,

    @SerializedName("pressure")
    var pressure: Long ,

    @SerializedName("humidity")
    var humidity: Long ,

    @SerializedName("temp_min")
    var tempMin: Float ,

    @SerializedName("temp_max")
    var tempMax: Float ,

    @SerializedName("temp_kf")
    var tempKF: Float

)

