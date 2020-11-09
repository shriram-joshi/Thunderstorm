package com.tec9ers.thunderstorm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.model.SavedCity
import com.tec9ers.thunderstorm.utils.ClickListener
import com.tec9ers.thunderstorm.utils.RecyclerTouchListener
import com.tec9ers.thunderstorm.view.adapter.FavCitiesAdapter
import com.tec9ers.thunderstorm.viewmodel.FavCitiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_fav_cities.*
import javax.inject.Inject

@AndroidEntryPoint
class FavCitiesFragment : Fragment() {

    private val viewModel: FavCitiesViewModel by viewModels()

    private lateinit var realm: Realm
    private lateinit var realmResults: RealmResults<SavedCity>

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

        RecyclerTouchListener(
            requireContext(), rv_fav_cities,
            object : ClickListener {
                override fun onClick(view: View, position: Int) {
                }

                override fun onLongPress(view: View, position: Int) {
                    val cityTag = view.tag.toString()
                    MaterialAlertDialogBuilder(requireContext()).setTitle("Confirm Deletion")
                        .setMessage(
                            "Remove ${cityTag.split(",")[0]} from your favorites?"
                        )
                        .setPositiveButton("Yes, Remove") { dialog, _ ->
                            realm.beginTransaction()
                            realm.where<SavedCity>().equalTo("cityName", cityTag)
                                .findFirst()?.deleteFromRealm()
                            realm.commitTransaction()
                            dialog.dismiss()
                            Toast.makeText(
                                context,
                                "Removed ${cityTag.split(",")[0]}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.cancel()
                        }
                        .setCancelable(true)
                        .show()
                }
            }
        )

        val savedCities = mutableListOf<SavedCity>()
        realmResults = realm.where<SavedCity>().findAll()
        savedCities.addAll(realm.copyFromRealm(realmResults))
        viewModel.fetchCitiesData(savedCities)
        realmResults.addChangeListener(
            RealmChangeListener<RealmResults<SavedCity>> {
                savedCities.clear()
                savedCities.addAll(realm.copyFromRealm(realmResults))
                viewModel.fetchCitiesData(savedCities)
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
