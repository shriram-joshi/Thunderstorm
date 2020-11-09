package com.tec9ers.thunderstorm.utils

import android.view.View

interface ClickListener {
    fun onClick(view: View, position: Int)
    fun onLongPress(view: View, position: Int)
}
