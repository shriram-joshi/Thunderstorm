package com.tec9ers.thunderstorm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.model.SavedCity
import com.tec9ers.thunderstorm.view.adapter.FavCitiesAdapter
import com.tec9ers.thunderstorm.viewmodel.FavCitiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_fav_cities.*
import javax.inject.Inject

@AndroidEntryPoint
class FavCitiesFragment : Fragment() {

    private val viewModel: FavCitiesViewModel by viewModels()

    lateinit var realm: Realm

    @Inject
    lateinit var adapter: FavCitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getCurrentCitiesLiveData()
            .observe(
                viewLifecycleOwner,
                { response ->
                    adapter.setData(response)
                }
            )
        return inflater.inflate(R.layout.fragment_fav_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floating_button.setOnClickListener {
            FavCitiesFragmentDirections.actionSavedToSearch(1)
                .run { findNavController().navigate(this) }
        }
        rv_fav_cities.adapter = adapter
        rv_fav_cities.layoutManager = LinearLayoutManager(requireActivity())

        realm = Realm.getDefaultInstance()
        try {
            val savedCities = realm.where<SavedCity>().findAll()
            viewModel.fetchCitiesData(savedCities)
        } finally {
            realm.close()
        }
    }
}
