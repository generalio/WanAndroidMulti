package com.generals.module.main.model.network

import com.generals.module.main.model.bean.NavigationResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

/**
 * description ： TODO:类的作用
 * date : 2025/4/3 10:34
 */
interface NavigationService {

    @GET("/navi/json")
    fun getNavigationInfo() : Observable<NavigationResponse>

}