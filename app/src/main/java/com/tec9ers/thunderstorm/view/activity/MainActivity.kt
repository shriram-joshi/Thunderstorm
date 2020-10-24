package com.tec9ers.thunderstorm.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.GravityCompat.START
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.tec9ers.thunderstorm.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_controller_main) as NavHostFragment
        navController = navHostFragment.navController
        side_nav_view.setNavigationItemSelectedListener(this)

        // Set this to the start destination of the fragment container
        side_nav_view.setCheckedItem(R.id.nav_home)
    }

    override fun onBackPressed() {
        if(side_nav_drawer.isDrawerOpen(START) && side_nav_drawer.isDrawerVisible(START))
            side_nav_drawer.close()
        else
            super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
             R.id.nav_home -> {
                 if (navController.currentDestination?.id !=  R.id.homeFragment){
                     navController.navigate(R.id.homeFragment)
                     side_nav_drawer.close()
                 }
            }
            R.id.nav_fav -> {
                if (navController.currentDestination?.id != R.id.favCities){
                    navController.navigate(R.id.favCities)
                    side_nav_drawer.close()
                }
            }
            R.id.nav_settings -> {
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_about -> {
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}