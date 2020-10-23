package com.tec9ers.thunderstorm.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.model.CurrentWeatherResponse
import com.tec9ers.thunderstorm.utils.FormatUtils
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@ActivityContext
class FavCitiesAdapter @Inject constructor(
    @ActivityContext val context: Context,
    private val format: FormatUtils
) :
    RecyclerView.Adapter<FavCitiesAdapter.FavCitiesViewHolder>() {

    private var data: List<CurrentWeatherResponse>? = null

    fun setData(data: List<CurrentWeatherResponse>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavCitiesViewHolder {
        return FavCitiesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavCitiesViewHolder, position: Int) {
        data?.get(position)?.let { holder.populate(it, format) }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class FavCitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun populate(currentWeatherResponse: CurrentWeatherResponse, format: FormatUtils) {
            with(itemView, {

            })
        }
    }
}