package com.tec9ers.thunderstorm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.utils.FormatUtils
import com.tec9ers.thunderstorm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var formatUtils :FormatUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel.oneCallApiLiveData.observe(
            viewLifecycleOwner,
            { oneCallAPIResponse ->
                oneCallAPIResponse?.apply {
                    with(formatUtils) {
                        tv_feels_like_temp.text = formatTemp(current.feelsLike)
                        tv_temp_main.text = formatTemp(current.temp)
                        tv_wind_speed.text = formatWindSpeed(current.windSpeed, current.windDeg)
                        tv_weather_condition.text = current.weather[0].description
                        tv_sun_rise.text = formatTime(current.sunrise)
                        tv_sun_set.text = formatTime(current.sunset)
                        tv_uv_index.text = formatUVI(current.uvi)
                        tv_pressure.text  = formatPressure(current.pressure)
                        tv_humidity.text  = formatHumidity(current.humidity)
                        tv_visibility.text = formatVisibility(current.visibility)
                    }
                }

            })

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun formatTemp(temp: Double): String {
        return getString(R.string.temp, temp)
    }

    private fun formatWindSpeed(windSpeed: Double, direction: Int): String {
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
        return getString(R.string.wind_speed, windSpeed) + " " + dir
    }
}