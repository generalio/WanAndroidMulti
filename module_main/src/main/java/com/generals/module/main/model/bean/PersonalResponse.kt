package com.generals.module.main.model.bean

/**
 * description ： TODO:类的作用
 * date : 2025/4/3 11:12
 */
data class PersonalResponse(
    val `data`: Datas,
    val errorCode: Int,
    val errorMsg: String
)

data class Datas(
    val userInfo: PersonalInfo
)

data class PersonalInfo(
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val id: Int,
    val username: String
)