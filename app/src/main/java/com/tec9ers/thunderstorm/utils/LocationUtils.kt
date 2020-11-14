package com.tec9ers.thunderstorm.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.tec9ers.thunderstorm.R

object LocationUtils {
    val REQUEST_CODE = 1001
    val permission = Manifest.permission.ACCESS_FINE_LOCATION
    fun checkPermissionStatus(activity: Activity):Boolean{
        return (ContextCompat.checkSelfPermission(
                activity,
                LocationUtils.permission
            ) != PackageManager.PERMISSION_GRANTED
        )
    }

    fun checkPermission(activity: Activity) {
        when {
            shouldShowRequestPermissionRationale(activity, permission) -> {
                //Dialog Builder
                AlertDialog.Builder(activity).apply {
                    setTitle(R.string.permission_dialog_title)
                    setMessage(R.string.location_permission_body)
                    setPositiveButton(
                        R.string.permission_positive_button
                    ) { _, _ ->
                        requestPermissions(
                            activity,
                            arrayOf(permission),
                            REQUEST_CODE
                        )
                    }
                    setNegativeButton(R.string.permission_negative_button) { p1, _ ->
                        p1.dismiss()
                    }
                }.create().show()

            }
            else -> {
                // Handle if user has been asked for Permission before
                // You can directly ask for the permission.
                requestPermissions(
                    activity,
                    arrayOf(permission),
                    REQUEST_CODE
                )
            }
        }
    }
}