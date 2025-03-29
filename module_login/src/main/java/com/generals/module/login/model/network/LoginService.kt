package com.generals.module.login.model.network

import com.generals.module.login.model.bean.PersonalInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * description ： TODO:类的作用
 * date : 2025/3/28 10:00
 */
interface LoginService {

    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<PersonalInfo>

    @FormUrlEncoded
    @POST("/user/register")
    fun sign(@Field("username") username: String, @Field("password") password: String, @Field("repassword") repassword: String) : Observable<PersonalInfo>
}