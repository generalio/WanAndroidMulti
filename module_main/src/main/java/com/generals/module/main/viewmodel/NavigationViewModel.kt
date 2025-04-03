package com.generals.module.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generals.module.main.model.bean.NavigationInfo
import com.generals.module.main.model.network.MainNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * description ： TODO:类的作用
 * date : 2025/4/3 10:47
 */
class NavigationViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    val navigationInfo : LiveData<List<NavigationInfo>> get() = _navigationInfo
    private val _navigationInfo : MutableLiveData<List<NavigationInfo>> = MutableLiveData()

    fun getNavigationInfo() {
        val disposable = MainNetwork.getNavigationInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { response ->
                _navigationInfo.postValue(response.data)
            }
            .doOnError { 
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

}