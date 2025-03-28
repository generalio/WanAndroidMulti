package com.generals.lib.base.util

import com.generals.lib.base.BaseApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * description ： Retrofit构建器
 * date : 2025/3/27 20:40
 */
object ServiceCreator {

    private val BASE_URL = "https://www.wanandroid.com/"

    val client = OkHttpClient.Builder()
        .cookieJar(CookieRemember(BaseApp.context))
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    inline fun <reified T> create() : T = retrofit.create(T::class.java)

}