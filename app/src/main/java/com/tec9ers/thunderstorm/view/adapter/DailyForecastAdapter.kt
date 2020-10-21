package com.tec9ers.thunderstorm.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tec9ers.thunderstorm.model.onecallapi.Daily
import com.tec9ers.thunderstorm.utils.FormatUtils
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@ActivityContext
class DailyForecastAdapter @Inject constructor(@ActivityContext val context: Context, val format: FormatUtils) :
    RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder>() {

    val data: ArrayList<Daily>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class DailyForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}