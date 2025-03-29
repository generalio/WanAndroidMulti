package com.generals.module.login.model.network


import com.generals.lib.base.util.ServiceCreator
import com.generals.module.login.model.bean.PersonalInfo
import io.reactivex.rxjava3.core.Observable

/**
 * description ： TODO:类的作用
 * date : 2025/3/28 10:07
 */
object LoginNetwork {

    private val loginService = ServiceCreator.create<LoginService>()

    fun login(username: String, password: String) : Observable<PersonalInfo> = loginService.login(username, password)
    fun sign(username: String, password: String, repassword: String) : Observable<PersonalInfo> = loginService.sign(username, password, repassword)

}