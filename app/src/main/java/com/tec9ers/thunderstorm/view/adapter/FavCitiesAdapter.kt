package com.tec9ers.thunderstorm.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.model.CurrentWeatherResponse
import com.tec9ers.thunderstorm.utils.FormatUtils
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.android.synthetic.main.item_fav_city.view.*
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_fav_city, parent, false)
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
            with(
                itemView,
                {
                    this.tag = currentWeatherResponse.name
                    city_name_tv.text = currentWeatherResponse.name
                    city_current_temp_tv.text =
                        format.formatTemp(currentWeatherResponse.main.temp.toDouble())
                    city_max_temp_tv.text =
                        format.formatTemp(currentWeatherResponse.main.tempMax.toDouble())
                    city_max_temp_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(context, R.drawable.ic_arrow_upward_24px),
                        null
                    )
                    city_min_temp_tv.text =
                        format.formatTemp(currentWeatherResponse.main.tempMin.toDouble())
                    city_min_temp_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(context, R.drawable.ic_arrow_downward_24px),
                        null
                    )
                    weather_desc_tv.text = currentWeatherResponse.weather[0].description
                }
            )
        }
    }
}
