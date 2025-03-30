package com.generals.module.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generals.module.main.model.network.MainNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * description ： TODO:类的作用
 * date : 2025/3/30 20:36
 */
class MainViewModel : ViewModel() {

    val logoutLivedata: LiveData<Int> get() = _logoutLiveData
    private val _logoutLiveData : MutableLiveData<Int> = MutableLiveData()

    val compositeDisposable = CompositeDisposable()

    fun logout() {

        val disposable = MainNetwork.logout()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                _logoutLiveData.postValue(999)
            }
            .doOnError {error ->
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${error.stackTrace}")
            }
            .subscribe()

        compositeDisposable.add(disposable)
    }
}