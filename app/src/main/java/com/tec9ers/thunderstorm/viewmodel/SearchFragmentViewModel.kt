package com.tec9ers.thunderstorm.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tec9ers.thunderstorm.data.service.SearchRepository
import com.tec9ers.thunderstorm.model.searchapi.SearchResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchFragmentViewModel @ViewModelInject constructor(val searchRepository: SearchRepository) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _searchResponseLiveData: MutableLiveData<SearchResponse> = MutableLiveData()

    fun getSearchResponseLiveData(): LiveData<SearchResponse> {
        return _searchResponseLiveData
    }

    fun fetchSearchResponseLiveData(query: String) {
        searchRepository.searchApiService.getSuggestedCities(query)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<SearchResponse> {
                override fun onSubscribe(disposable: Disposable?) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(searchResponse: SearchResponse?) {
                    _searchResponseLiveData.value = searchResponse
                }

                override fun onError(error: Throwable?) {
                }
            })
    }
}
