package com.tec9ers.thunderstorm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.utils.DataStoreUtils
import com.tec9ers.thunderstorm.view.adapter.FavCitiesAdapter
import com.tec9ers.thunderstorm.viewmodel.FavCitiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_fav_cities.*
import javax.inject.Inject

@AndroidEntryPoint
class FavCities : Fragment() {

    private val viewModel: FavCitiesViewModel by viewModels()

    // For Testing
    private val citiesListFake = listOf("Pune", "Mumbai")

    @Inject
    lateinit var dataStoreUtils: DataStoreUtils

    @Inject
    lateinit var adapter: FavCitiesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floating_button.setOnClickListener(View.OnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_favCities_to_searchFragment)
        })
        rv_fav_cities.adapter = adapter
        rv_fav_cities.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**dataStoreUtils.citiesFlow.asLiveData()
        .observe(viewLifecycleOwner,
        { citiesList -> setupRecyclerView(citiesListFake as MutableList<String>) })*/
        setupRecyclerView(citiesListFake as MutableList<String>)
        return inflater.inflate(R.layout.fragment_fav_cities, container, false)
    }

    private fun setupRecyclerView(citiesList: MutableList<String>) {

        viewModel.getCurrentCitiesLiveData(citiesListFake)
            .observe(viewLifecycleOwner, { response ->
                adapter.setData(response)
            })
    }
}