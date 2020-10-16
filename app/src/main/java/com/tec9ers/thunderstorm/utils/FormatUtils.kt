package com.tec9ers.thunderstorm.utils

import android.annotation.SuppressLint
import android.content.Context
import com.tec9ers.thunderstorm.R
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ActivityScoped
class FormatUtils @Inject constructor(@ApplicationContext val context: Context) {

    fun formatTemp(temp: Double): String {
        return context.getString(R.string.temp, temp)
    }

    fun formatWindSpeed(windSpeed: Double, direction: Int): String {
        val dir = when (direction / 45) {
            0 -> "N"
            1 -> "NE"
            2 -> "E"
            3 -> "SE"
            4 -> "S"
            5 -> "SW"
            6 -> "W"
            7 -> "NW"
            17 -> "N"
            else -> ""
        }
        return context.getString(R.string.wind_speed, windSpeed) + " " + dir
    }

    fun formatTime(time: Long): String {
        val date = Date(time * 1000)
        val format = DateFormat.getTimeInstance()
        return format.format(date)
    }

    fun formatPressure(pressure: Int): String {
        val atm = pressure*0.000987
        return (context.getString(R.string.pressure,atm))
    }

    fun formatVisibility(visibility : Int): String {
        val km = visibility*0.001
        return context.getString(R.string.visibility,km)
    }

    fun formatHumidity(humidity: Int): String {
        return context.getString(R.string.humidity,humidity) + " %"
    }

    fun formatUVI(UVI : Double): String{
        return context.getString(R.string.uv_index,UVI)
    }
}
