package com.tec9ers.thunderstorm.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tec9ers.thunderstorm.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}