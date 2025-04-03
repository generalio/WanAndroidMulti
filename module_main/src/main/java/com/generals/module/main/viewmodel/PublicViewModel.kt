package com.generals.module.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generals.module.main.model.bean.PassageInfo
import com.generals.module.main.model.bean.PublicAuthorInfo
import com.generals.module.main.model.network.MainNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * description ： TODO:类的作用
 * date : 2025/4/2 21:06
 */
class PublicViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    val authorInfoLiveData : LiveData<List<PublicAuthorInfo>> get() = _authorInfoLiveData
    private val _authorInfoLiveData: MutableLiveData<List<PublicAuthorInfo>> = MutableLiveData()

    val publicPassageLiveData : LiveData<List<PassageInfo>> get() = _publicPassageLiveData
    private val _publicPassageLiveData : MutableLiveData<List<PassageInfo>> = MutableLiveData()

    fun getPublicAuthor() {
        val disposable = MainNetwork.getPublicAuthor()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { response ->
                _authorInfoLiveData.postValue(response.data)
            }
            .doOnError { error ->
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${error.stackTrace}")
            }
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun getPublicPassage(page: Int, id: Int) {
        val disposable = MainNetwork.getPublicPassage(page, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { response ->
                _publicPassageLiveData.postValue(response.data.datas)
            }
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }
}