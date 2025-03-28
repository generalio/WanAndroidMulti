package com.generals.module.login.model.network

import com.generals.module.login.model.bean.PersonalInfo
import com.generals.module.login.model.bean.PostBody
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * description ： TODO:类的作用
 * date : 2025/3/28 10:00
 */
interface LoginService {

    @POST("user/login")
    fun login(@Body body: PostBody.LoginBody) : Observable<PersonalInfo>

    @POST("user/register")
    fun sign(@Body body: PostBody.SignBody) : Observable<PersonalInfo>
}