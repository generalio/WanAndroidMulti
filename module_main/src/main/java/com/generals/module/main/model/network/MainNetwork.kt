package com.generals.module.main.model.network

import com.generals.lib.base.util.ServiceCreator

/**
 * description ： TODO:类的作用
 * date : 2025/3/30 20:35
 */
object MainNetwork {

    val logoutService = ServiceCreator.create<LogoutService>()
    val homeService = ServiceCreator.create<HomeService>()
    val publicService = ServiceCreator.create<PublicService>()
    val naviationService = ServiceCreator.create<NavigationService>()

    fun logout() = logoutService.logout()

    fun getCarousel() = homeService.getCarousel()
    fun getHomePassage(page: Int) = homeService.getHomePassage(page)
    fun getPersonalInfo() = homeService.getPersonalInfo()

    fun getPublicAuthor() = publicService.getPublicAuthor()
    fun getPublicPassage(page: Int, id: Int) = publicService.getPublicPassage(page, id)

    fun getNavigationInfo() = naviationService.getNavigationInfo()
}