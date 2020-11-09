package com.tec9ers.thunderstorm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.model.SavedCity
import com.tec9ers.thunderstorm.utils.ClickListener
import com.tec9ers.thunderstorm.utils.RecyclerTouchListener
import com.tec9ers.thunderstorm.view.adapter.CitiesSearchAdapter
import com.tec9ers.thunderstorm.viewmodel.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchFragmentViewModel: SearchFragmentViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private val args: SearchFragmentArgs by navArgs()
    lateinit var realm: Realm

    @Inject
    lateinit var citiesSearchAdapter: CitiesSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        searchFragmentViewModel.getSearchResponseLiveData().observe(
            viewLifecycleOwner,
            {
                citiesSearchAdapter.setData(it.embedded.cities)
            }
        )

        searchFragmentViewModel.getCityLocationLiveData().observe(
            viewLifecycleOwner,
            {
                when (args.origin) {
                    0 ->
                        SearchFragmentDirections.actionResultToHome(
                            it.location.latlon.latitude.toFloat(),
                            it.location.latlon.longitude.toFloat(),
                            it.full_name
                        )
                            .run { findNavController().navigate(this) }
                    1 -> {
                        realm = Realm.getDefaultInstance()
                        try {
                            val savedCity = SavedCity(
                                it.full_name,
                                it.location.latlon.latitude.toFloat(),
                                it.location.latlon.longitude.toFloat()
                            )
                            realm.beginTransaction()
                            realm.copyToRealm(savedCity)
                            realm.commitTransaction()
                            SearchFragmentDirections.actionSearchToSaved()
                                .run { findNavController().navigate(this) }
                        } catch (exception: RealmPrimaryKeyConstraintException) {
                            Toast.makeText(context, "This city already exists in your favorites.", Toast.LENGTH_SHORT).show()
                        } finally {
                            realm.close()
                        }
                    }
                }
            }
        )
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observableSearchView()

        rv_cities_search.adapter = citiesSearchAdapter
        rv_cities_search.layoutManager = LinearLayoutManager(requireContext())
        rv_cities_search.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        RecyclerTouchListener(
            requireContext(), rv_cities_search,
            object :
                ClickListener {
                override fun onClick(view: View, position: Int) {
                    searchFragmentViewModel.fetchCityLocationLiveData(
                        citiesSearchAdapter.getData()?.get(position)!!.links.cityItem.href
                    )
                }

                override fun onLongPress(view: View, position: Int) {
                }
            }
        )
    }

    private fun observableSearchView() {
        Observable.create(
            (
                ObservableOnSubscribe<String> {
                    search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            if (query != null || query == "") {
                                searchFragmentViewModel.fetchSearchResponseLiveData(query)
                            }
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            if (!it.isDisposed) {
                                it.onNext(newText)
                            }
                            return false
                        }
                    })
                }
                )
        ).debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.newThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(disposable: Disposable?) {
                    compositeDisposable.add(disposable)
                }

                override fun onNext(query: String?) {
                    if (query != null) {
                        searchFragmentViewModel.fetchSearchResponseLiveData(query)
                    }
                }

                override fun onError(error: Throwable?) {
                }

                override fun onComplete() {
                }
            })
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }
}
