package com.generals.module.main.model.network

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * description ： TODO:类的作用
 * date : 2025/3/30 20:33
 */
interface LogoutService {

    @GET("/user/logout/json")
    fun logout(): Observable<ResponseBody>
}