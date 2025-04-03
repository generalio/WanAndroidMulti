package com.generals.module.main.model.network

import com.generals.lib.base.util.ServiceCreator
import com.generals.module.main.model.bean.PassageResponse
import com.generals.module.main.model.bean.PublicAuthorResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * description ： TODO:类的作用
 * date : 2025/4/2 20:52
 */
interface PublicService {

    @GET("/wxarticle/chapters/json  ")
    fun getPublicAuthor() : Observable<PublicAuthorResponse>

    @GET("/wxarticle/list/{id}/{page}/json")
    fun getPublicPassage(@Path("page") page: Int, @Path("id") id: Int) : Observable<PassageResponse>
}