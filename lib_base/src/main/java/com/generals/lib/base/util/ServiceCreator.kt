package com.generals.lib.base.util

import com.generals.lib.base.BaseApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * description ： Retrofit构建器
 * date : 2025/3/27 20:40
 */
object ServiceCreator {

    private val BASE_URL = "https://www.wanandroid.com"
    //private val loggingInterceptor = LoggingInterceptor() Okhttp拦截器

    val client = OkHttpClient.Builder()
        .cookieJar(CookieRemember(BaseApp.context))
        //.addInterceptor(loggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    inline fun <reified T> create() : T = retrofit.create(T::class.java)

}