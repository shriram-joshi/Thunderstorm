package com.tec9ers.thunderstorm.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tec9ers.thunderstorm.data.QueryParams
import com.tec9ers.thunderstorm.data.Repository
import com.tec9ers.thunderstorm.model.CurrentWeatherResponse
import com.tec9ers.thunderstorm.model.SavedCity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FavCitiesViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val currentWeatherLiveData: MutableLiveData<MutableList<CurrentWeatherResponse>> by lazy {
        MutableLiveData<MutableList<CurrentWeatherResponse>>()
    }

    fun getCurrentCitiesLiveData(): LiveData<MutableList<CurrentWeatherResponse>> {
        return currentWeatherLiveData
    }

    fun fetchCitiesData(cityList: List<SavedCity>) {
        val currentWeatherResponseList = mutableListOf<CurrentWeatherResponse>()
        Observable.fromIterable(cityList)
            .flatMap { city ->
                repository.getCurrentWeatherSingle(
                    QueryParams().getResponseByCoordinates(city.lat, city.lon)
                ).toObservable()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<CurrentWeatherResponse> {

                override fun onSubscribe(disposable: Disposable?) {
                    compositeDisposable.add(disposable)
                }

                override fun onNext(currentWeatherResponse: CurrentWeatherResponse?) {
                    if (currentWeatherResponse != null) {
                        currentWeatherResponseList.add(currentWeatherResponse)
                        currentWeatherLiveData.value = currentWeatherResponseList
                    }
                }

                override fun onError(error: Throwable?) {
                }

                override fun onComplete() {
                }
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
