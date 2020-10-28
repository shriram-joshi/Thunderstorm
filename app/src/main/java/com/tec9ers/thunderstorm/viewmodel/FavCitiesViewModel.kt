package com.tec9ers.thunderstorm.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tec9ers.thunderstorm.data.QueryParams
import com.tec9ers.thunderstorm.data.Repository
import com.tec9ers.thunderstorm.model.CurrentWeatherResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FavCitiesViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _currentWeather: MutableLiveData<MutableList<CurrentWeatherResponse>> by lazy {
        MutableLiveData<MutableList<CurrentWeatherResponse>>()
    }

    fun getCurrentCitiesLiveData(citiesList: List<String>): LiveData<MutableList<CurrentWeatherResponse>> {
        fetchCitiesData(citiesList)
        return _currentWeather
    }

    private fun fetchCitiesData(cityList: List<String>) {
        val currentWeatherResponseList = mutableListOf<CurrentWeatherResponse>()
        Observable.fromIterable(cityList)
            .flatMap { city ->
                repository.getCurrentWeatherSingle(QueryParams().getResponseByCity(city))
                    .toObservable()
            }.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<CurrentWeatherResponse> {
                    override fun onSubscribe(disposable: Disposable?) {
                        compositeDisposable.add(disposable)
                    }

                    override fun onNext(currentWeatherResponse: CurrentWeatherResponse?) {
                        if (currentWeatherResponse != null) {
                            currentWeatherResponseList.add(currentWeatherResponse)
                            _currentWeather.value = currentWeatherResponseList
                        }
                    }

                    override fun onError(error: Throwable?) {
                        // TODO handle error here
                    }

                    override fun onComplete() {
                        // TODO handles on complete
                    }
                }
            )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
