package com.tec9ers.thunderstorm.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.model.CurrentWeatherResponse
import com.tec9ers.thunderstorm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val TAG = "lollo"
    @Inject
    lateinit var retrofit : Retrofit
    val viewModel : HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.currentWeatherLiveData()
         .observe(this,
             { response -> Log.d(TAG, "onCreate: " + response.name) }
         )
    }
}