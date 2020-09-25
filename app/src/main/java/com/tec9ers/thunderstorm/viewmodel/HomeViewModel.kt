package com.tec9ers.thunderstorm.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tec9ers.thunderstorm.data.QueryParams
import com.tec9ers.thunderstorm.data.Repository
import com.tec9ers.thunderstorm.model.CurrentWeatherResponse
import com.tec9ers.thunderstorm.model.onecallapi.OneCallAPIResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel @ViewModelInject constructor (private val repository: Repository) : ViewModel(){

    private val currentWeatherLiveData: MutableLiveData<CurrentWeatherResponse> = MutableLiveData()
    private val oneCallApiLiveData: MutableLiveData<OneCallAPIResponse> = MutableLiveData()
    private val compositeDisposable :CompositeDisposable = CompositeDisposable()

    fun currentWeatherLiveData(): LiveData<CurrentWeatherResponse> {
        if (currentWeatherLiveData.value == null)
            fetchWeatherLiveData()
        return currentWeatherLiveData
    }

    fun oneCallAPIData() : LiveData<OneCallAPIResponse> {
        repository.getOneCallApiDataSingle(QueryParams().getResponseByCoordinates(18.558, 73.804))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<OneCallAPIResponse> {
                override fun onSubscribe(d: Disposable?) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: OneCallAPIResponse?) {
                    oneCallApiLiveData.postValue(t)
                }

                override fun onError(e: Throwable?) {
                    Log.d("OneCallAPI", "Failed: " + e.toString())
                }
            })
        return oneCallApiLiveData
    }

    private fun fetchWeatherLiveData() {
        repository.getCurrentWeatherSingle(QueryParams().getResponseByCity("pune"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CurrentWeatherResponse> {
                override fun onSubscribe(d: Disposable?) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: CurrentWeatherResponse?) {
                    currentWeatherLiveData.postValue(t)
                }

                override fun onError(e: Throwable?) {
                    Log.d("lollo", "onError: " + e.toString())
                }
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}