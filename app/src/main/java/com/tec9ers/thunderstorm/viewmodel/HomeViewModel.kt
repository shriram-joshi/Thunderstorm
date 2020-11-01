package com.tec9ers.thunderstorm.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tec9ers.thunderstorm.data.QueryParams
import com.tec9ers.thunderstorm.data.Repository
import com.tec9ers.thunderstorm.model.onecallapi.OneCallAPIResponse
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    private val oneCallApiLiveData = MutableLiveData<OneCallAPIResponse>()

    fun getOneCallAPIData(): LiveData<OneCallAPIResponse> {
        return oneCallApiLiveData
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun fetchOneCallAPIData(lat: Float = 18.558f, lon: Float = 73.804f) {
        repository.getOneCallApiDataSingle(
            QueryParams().getResponseByCoordinates(lat, lon).excludeField("minutely")
        )
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<OneCallAPIResponse> {
                override fun onSubscribe(disposable: Disposable?) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(oneCallAPIResponse: OneCallAPIResponse?) {
                    oneCallApiLiveData.postValue(oneCallAPIResponse)
                }

                override fun onError(throwable: Throwable?) {
                    Log.d("OneCallAPI", "Failed: " + throwable.toString())
                }
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
        Log.e("HomeViewModel", "Cleared")
        super.onCleared()
    }
}
