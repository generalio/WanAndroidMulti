package com.generals.module.login.model.bean

data class PersonalInfo(
    val `data`: Data,
    val errorCode: Int,
    val errorMsg: String
)

data class Data(
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val id: Int,
    val username: String
)