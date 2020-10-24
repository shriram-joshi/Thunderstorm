package com.tec9ers.thunderstorm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tec9ers.thunderstorm.R
import com.tec9ers.thunderstorm.view.adapter.CitiesSearchAdapter
import com.tec9ers.thunderstorm.viewmodel.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val searchFragmentViewModel: SearchFragmentViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var citiesSearchAdapter: CitiesSearchAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableSearchView()
        rv_cities_search.adapter = citiesSearchAdapter
        rv_cities_search.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchFragmentViewModel.getSearchResponseLiveData().observe(viewLifecycleOwner, {
            citiesSearchAdapter.setData(it.embedded.cities)
        })

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    private fun observableSearchView() {
        Observable.create((ObservableOnSubscribe<String> {
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
        })).debounce(500, TimeUnit.MILLISECONDS)
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