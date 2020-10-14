package com.tec9ers.thunderstorm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.databinding.FragmentHomeBinding
import com.tec9ers.thunderstorm.model.onecallapi.OneCallAPIResponse
import com.tec9ers.thunderstorm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel.oneCallApiLiveData.observe(
            viewLifecycleOwner,
            { oneCallAPIResponse ->
                with(binding) {
                    tvFeelsLikeTemp.text = formatTemp(oneCallAPIResponse!!.current.feelsLike)
                    tvTempMain.text = formatTemp(oneCallAPIResponse.current.temp)
                    tvWindSpeed.text = formatWindSpeed(oneCallAPIResponse.current.windSpeed,oneCallAPIResponse.current.windDeg)
                    tvWeatherCondition.text = oneCallAPIResponse.current.weather[0].description
                }
            })

        return binding.root
    }

    private fun formatTemp(temp: Double): String {
        return getString(R.string.temp, temp)
    }

    private fun formatWindSpeed(windSpeed: Double , direction: Int): String {
        val dir = when(direction){
            1->"N"
            2,3,4->"NE"
            5-> "E"
            6,7,8->"SE"
            9->"S"
            10,11,12->"SW"
            13->"W"
            14,15,16->"NW"
            17->"N"
            else-> ""
        }
        return getString(R.string.wind_speed, windSpeed) + " " + dir
    }
}