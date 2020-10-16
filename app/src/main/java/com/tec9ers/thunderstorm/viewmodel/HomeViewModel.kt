package com.tec9ers.thunderstorm.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tec9ers.thunderstorm.data.QueryParams
import com.tec9ers.thunderstorm.data.Repository
import com.tec9ers.thunderstorm.model.onecallapi.OneCallAPIResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    private val _oneCallApiLiveData: MutableLiveData<OneCallAPIResponse> by lazy {
        MutableLiveData<OneCallAPIResponse>().also {
            fetchOneCallAPIData()
        }
    }

    val oneCallApiLivaData : LiveData<OneCallAPIResponse>
    get() = _oneCallApiLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private fun fetchOneCallAPIData() {
        repository.getOneCallApiDataSingle(QueryParams().getResponseByCoordinates(18.558, 73.804))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<OneCallAPIResponse> {
                override fun onSubscribe(d: Disposable?) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(oneCallAPIResponse: OneCallAPIResponse?) {
                    _oneCallApiLiveData.value = oneCallAPIResponse
                }

                override fun onError(e: Throwable?) {
                    Log.d("OneCallAPI", "Failed: " + e.toString())
                }
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}