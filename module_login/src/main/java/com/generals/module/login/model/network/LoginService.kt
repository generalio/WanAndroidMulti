package com.generals.module.login.model.network

import com.generals.module.login.model.bean.LoginBody
import com.generals.module.login.model.bean.PersonalInfo
import com.generals.module.login.model.bean.SignBody
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * description ： TODO:类的作用
 * date : 2025/3/28 10:00
 */
interface LoginService {

    @Headers("Content-Type: application/json")
    @POST("/user/login")
    fun login(@Body loginRequest: LoginBody): Observable<PersonalInfo>

    @POST("/user/register")
    fun sign(@Body data: SignBody) : Observable<PersonalInfo>
}