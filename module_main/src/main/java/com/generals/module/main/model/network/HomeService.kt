package com.generals.module.main.model.network

import com.generals.module.main.model.bean.CarouselResponse
import com.generals.module.main.model.bean.PassageResponse
import com.generals.module.main.model.bean.PersonalResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * description ： TODO:类的作用
 * date : 2025/4/1 17:27
 */
interface HomeService {

    @GET("/banner/json")
    fun getCarousel() : Observable<CarouselResponse>

    @GET("/article/list/{page}/json")
    fun getHomePassage(@Path("page") page: Int) : Observable<PassageResponse>

    @GET("/user/lg/userinfo/json")
    fun getPersonalInfo() : Observable<PersonalResponse>

}