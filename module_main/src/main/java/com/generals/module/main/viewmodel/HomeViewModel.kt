package com.generals.module.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generals.module.main.model.bean.CarouselInfo
import com.generals.module.main.model.bean.PassageInfo
import com.generals.module.main.model.bean.PersonalInfo
import com.generals.module.main.model.bean.PersonalResponse
import com.generals.module.main.model.network.MainNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * description ： TODO:类的作用
 * date : 2025/4/1 17:31
 */
class HomeViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val carouselLiveData: LiveData<List<CarouselInfo>> get() = _carouselLiveData
    private val _carouselLiveData : MutableLiveData<List<CarouselInfo>> = MutableLiveData()

    val homePassageLiveData : LiveData<List<PassageInfo>> get() = _homePassageLiveData
    private val _homePassageLiveData : MutableLiveData<List<PassageInfo>> = MutableLiveData()

    val personalInfo : LiveData<PersonalResponse> get() = _personalInfo
    private val _personalInfo : MutableLiveData<PersonalResponse> = MutableLiveData()

    fun getCarousel() {
        val disposable = MainNetwork.getCarousel()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { result ->
                _carouselLiveData.postValue(result.data)
            }
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun getPassage(page: Int) {
        val disposable = MainNetwork.getHomePassage(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { result ->
                _homePassageLiveData.postValue(result.data.datas)
            }
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun getPersonalInfo() {
        val disposable = MainNetwork.getPersonalInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                _personalInfo.postValue(it)
            }
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }
}