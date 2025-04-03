package com.generals.module.main.model.bean

/**
 * description ： TODO:类的作用
 * date : 2025/4/3 10:38
 */
data class NavigationResponse(
    val data: List<NavigationInfo>,
    val errorCode: Int,
    val errorMsg: String
)

data class NavigationInfo(
    val articles: List<PassageInfo>,
    val name: String
)