package com.tec9ers.thunderstorm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.model.CurrentWeatherResponse
import com.tec9ers.thunderstorm.model.SavedCity
import com.tec9ers.thunderstorm.view.adapter.FavCitiesAdapter
import com.tec9ers.thunderstorm.viewmodel.FavCitiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_fav_cities.*
import java.lang.IllegalStateException
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
                viewLifecycleOwner
            ) { response ->
                adapter.data = response
            }
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
        swipeListener()
        realm = Realm.getDefaultInstance()
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

    private fun swipeListener() {
        var snackbar: Snackbar? = null
        lateinit var deletedItem: CurrentWeatherResponse
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                snackbar?.dismiss()
                snackbar =
                    Snackbar.make(
                        fav_cities_relative_layout,
                        "Are you sure? ${viewHolder.itemView.tag}",
                        Snackbar.LENGTH_LONG
                    )
                snackbar?.setAction(
                    "UNDO"
                ) {
                    realm.cancelTransaction()
                    // add Method to restore city here
                    adapter.restoreItem(position, deletedItem)
                }
                val callback = object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        try {
                            realm.commitTransaction()
                        } catch (error: IllegalStateException) {
                            error.printStackTrace()
                        }
                    }

                    override fun onShown(sb: Snackbar?) {
                        super.onShown(sb)
                        deletedItem = adapter.data?.get(position)!!
                        adapter.remove(position)
                        realm.beginTransaction()
                        realm.where<SavedCity>().equalTo(
                            "cityName",
                            viewHolder.itemView.tag.toString()
                        )
                            .findFirst()
                            ?.deleteFromRealm()
                    }
                }
                snackbar?.addCallback(callback)
                snackbar?.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rv_fav_cities)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
