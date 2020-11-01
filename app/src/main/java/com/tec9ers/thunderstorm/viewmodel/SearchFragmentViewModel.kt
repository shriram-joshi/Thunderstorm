package com.tec9ers.thunderstorm.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tec9ers.thunderstorm.data.SearchRepository
import com.tec9ers.thunderstorm.model.searchapi.CityLocationContainer
import com.tec9ers.thunderstorm.model.searchapi.SearchResponse
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchFragmentViewModel @ViewModelInject constructor(val searchRepository: SearchRepository) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _searchResponseLiveData: MutableLiveData<SearchResponse> = MutableLiveData()
    private val _cityLocationLiveData: MutableLiveData<CityLocationContainer> = MutableLiveData()

    fun getSearchResponseLiveData(): LiveData<SearchResponse> {
        return _searchResponseLiveData
    }

    fun getCityLocationLiveData(): LiveData<CityLocationContainer> {
        return _cityLocationLiveData
    }

    fun fetchSearchResponseLiveData(query: String) {
        searchRepository.getSuggestionsSingle(query)
            .subscribeOn(Schedulers.newThread())
            .subscribe(object : SingleObserver<SearchResponse> {
                override fun onSubscribe(disposable: Disposable?) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(searchResponse: SearchResponse?) {
                    _searchResponseLiveData.postValue(searchResponse)
                }

                override fun onError(error: Throwable?) {
                }
            })
    }

    fun fetchCityLocationLiveData(url: String) {
        searchRepository.getCityLocationSingle(url)
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<CityLocationContainer> {
                override fun onSubscribe(disposable: Disposable?) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(cityLocationResponse: CityLocationContainer?) {
                    _cityLocationLiveData.postValue(cityLocationResponse)
                }

                override fun onError(throwable: Throwable?) {
                }
            })
    }
}
