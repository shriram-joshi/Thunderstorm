package com.tec9ers.thunderstorm.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val TAG = "lollo"
    @Inject
    lateinit var retrofit : Retrofit
    private val viewModel : HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.currentWeatherLiveData()
         .observe(this, { response -> Log.d(TAG, "onCreate: " + response.name) })

        api_test_btn.setOnClickListener {
            viewModel.oneCallAPIData()
            .observe(this as LifecycleOwner, { response -> Log.d("OneCall", response.timezone) })
        }
    }
}