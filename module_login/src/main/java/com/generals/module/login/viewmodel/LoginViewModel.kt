package com.generals.module.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generals.module.login.model.bean.PersonalInfo
import com.generals.module.login.model.dao.DataRemember
import com.generals.module.login.model.network.LoginNetwork
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * description ： TODO:类的作用
 * date : 2025/3/28 19:41
 */
class LoginViewModel : ViewModel() {

    val livedataLogin: LiveData<PersonalInfo> get() = _livedataLogin
    val livedataSign: LiveData<PersonalInfo> get() = _livedataUsername

    private val _livedataLogin: MutableLiveData<PersonalInfo> = MutableLiveData()
    private val _livedataUsername: MutableLiveData<PersonalInfo> = MutableLiveData()
    val compositeDisposable = CompositeDisposable()

    fun login(username: String, password: String) {
        val disposable = LoginNetwork.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { response ->
                Log.d("zzx", response.toString())
                _livedataLogin.postValue(response)
            }
            .doOnError { error ->
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${error.stackTrace}")
            }
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun sign(username: String, password: String, repassword: String) {
        val disposable = LoginNetwork.sign(username, password, repassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {response ->
                _livedataUsername.postValue(response)
            }
            .doOnError { error ->
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${error.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun passwordRemember(username: String, password: String) {
        DataRemember.rememberPassword(username, password)
    }

    fun initPassword() : Pair<String, String> = DataRemember.getPassword()
}