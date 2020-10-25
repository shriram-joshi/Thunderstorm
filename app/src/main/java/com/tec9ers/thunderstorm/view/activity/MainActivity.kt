package com.tec9ers.thunderstorm.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tec9ers.thunderstorm.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = (supportFragmentManager.findFragmentById(R.id.nav_controller_main) as NavHostFragment).navController
        side_nav_view.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (side_nav_drawer.isDrawerOpen(GravityCompat.START) && side_nav_drawer.isDrawerVisible(GravityCompat.START)) {
            side_nav_drawer.close()
        } else {
            super.onBackPressed()
        }
    }
}