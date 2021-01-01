package com.tec9ers.thunderstorm.view.fragment

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.matteobattilana.weather.PrecipType
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.model.onecallapi.OneCallAPIResponse
import com.tec9ers.thunderstorm.utils.FormatUtils
import com.tec9ers.thunderstorm.utils.LocationUtils
import com.tec9ers.thunderstorm.view.adapter.DailyForecastAdapter
import com.tec9ers.thunderstorm.view.adapter.HourlyForecastRecyclerViewAdapter
import com.tec9ers.thunderstorm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.NullPointerException
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()

    // TODO Create a userLocation Class that will make this easier
    // TODO fetch these from SharedPreferences
    private var userlat = 0.0
    private var userlon = 0.0

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var formatUtils: FormatUtils

    @Inject
    lateinit var hourlyForecastRecyclerViewAdapter: HourlyForecastRecyclerViewAdapter

    @Inject
    lateinit var dailyForecastAdapter: DailyForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        if (LocationUtils.checkPermissionStatus(requireActivity())) {
            getLastLocation()
        } else {
            LocationUtils.checkPermission(requireActivity())
        }

        homeViewModel.getOneCallAPIData().observe(viewLifecycleOwner) { oneCallAPIResponse ->
            setData(oneCallAPIResponse)
        }

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInvertedBG()
        // To prevent clipping of content across linearlayout rounded corners
        hourly_forecast_layout.clipToOutline = true

        hourly_forecast_rv.setupRecyclerView()
        hourly_forecast_rv.adapter = hourlyForecastRecyclerViewAdapter
        daily_forecast_rv.setupRecyclerView()
        daily_forecast_rv.adapter = dailyForecastAdapter

        val args: HomeFragmentArgs by navArgs()
        with(args) {
            if (lat != -200f && lon != -200f) {
                homeViewModel.fetchOneCallAPIData(lat, lon)
                main_card_title_tv.text = cityName
            } else {
                homeViewModel.fetchOneCallAPIData(userlat.toFloat(), userlon.toFloat())
            }
        }

        // TODO check if it can be moved to another method
        swipe_refresh_home.setOnRefreshListener {
            getLastLocation()
            swipe_refresh_home.isRefreshing = false
        }

        main_card_title_tv.setOnClickListener {
            HomeFragmentDirections.actionHomeToSearch(0)
                .run { findNavController().navigate(this) }
        }
        weather_view.apply {
            fadeOutPercent = 5F
            setBackgroundColor(Color.parseColor("#0000FF"))
            angle = 30
            setWeatherData(PrecipType.RAIN)
            emissionRate = 750f
            speed = 2000
        }
    }

    private fun setData(oneCallAPIResponse: OneCallAPIResponse) {
        oneCallAPIResponse.apply {
            with(formatUtils) {
                tv_feels_like_temp.text = formatTemp(current.feelsLike)
                tv_temp_main.text = formatTemp(current.temp)
                tv_wind_speed.text = formatWindSpeed(current.windSpeed, current.windDeg)
                tv_weather_condition.text = capitalizeDescription(current.weather[0].description)
                tv_sun_rise.text = formatTime(current.sunrise)
                tv_sun_set.text = formatTime(current.sunset)
                tv_uv_index.text = formatUVI(current.uvi)
                tv_pressure.text = formatPressure(current.pressure)
                tv_humidity.text = formatHumidity(current.humidity)
                tv_visibility.text = formatVisibility(current.visibility)
                main_card_title_tv.text = getUserCity(lat, lon)
                hourlyForecastRecyclerViewAdapter.setData(hourly)
                dailyForecastAdapter.setData(daily)
            }
        }
    }

    /**
     * This Function Inverts the GradientDrawable and Sets it as a Background to the TitleTV
     **/
    private fun setInvertedBG() {

        val gradientDrawable: GradientDrawable = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.hourly_forecast
        )?.mutate() as GradientDrawable
        gradientDrawable.orientation = GradientDrawable.Orientation.BOTTOM_TOP
        hourly_forecast_title_tv.background = gradientDrawable
    }

    private fun RecyclerView.setupRecyclerView() {
        this.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        this.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.HORIZONTAL
            )
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LocationUtils.REQUEST_CODE ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation()
                } else {
                    Toast.makeText(requireContext(), "permission not granted", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        val userLocation = fusedLocationProviderClient.getCurrentLocation(
            LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
            null
        )
        userLocation.addOnSuccessListener {
            if (it != null) {
                userlat = it.latitude
                userlon = it.longitude
                homeViewModel.fetchOneCallAPIData(userlat.toFloat(), userlon.toFloat())
            }
        }
        userLocation.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed to fetch Location", Toast.LENGTH_SHORT)
                .show()
        }
    }

    /** This function takes in @param lat and @param lon which are the latitudes and longitudes respectively
     * and @return value is a string containing locality (or city name) corresponding to the latitudes and longitudes
     **/
    private fun getUserCity(lat: Double, lon: Double): String {
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, lon, 1)
        return try {
            address[0].locality
        } catch (error: IndexOutOfBoundsException) {
            "Unable to decode"
        } catch (error: NullPointerException) {
            "Unable to decode"
        }
    }
}
