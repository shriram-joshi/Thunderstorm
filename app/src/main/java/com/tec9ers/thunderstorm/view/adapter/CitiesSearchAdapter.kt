package com.tec9ers.thunderstorm.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.model.searchapi.CityContainer
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.android.synthetic.main.item_city_search.view.*
import javax.inject.Inject

@ActivityContext
class CitiesSearchAdapter @Inject constructor(
    @ActivityContext val context: Context,
) :
    RecyclerView.Adapter<CitiesSearchAdapter.CitiesSearchViewHolder>() {

    private var data: List<CityContainer>? = null

    fun setData(data: List<CityContainer>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getData(): List<CityContainer>? {
        return this.data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesSearchViewHolder {
        return CitiesSearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_city_search, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CitiesSearchViewHolder, position: Int) {
        data?.get(position)?.let { holder.populate(it) }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class CitiesSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populate(cityContaier: CityContainer) {
            itemView.tv_search_name.text = cityContaier.fullName
        }
    }
}
