package com.generals.module.main.model.network

import com.generals.lib.base.util.ServiceCreator

/**
 * description ： TODO:类的作用
 * date : 2025/3/30 20:35
 */
object MainNetwork {

    val logoutService = ServiceCreator.create<LogoutService>()

    fun logout() = logoutService.logout()

}