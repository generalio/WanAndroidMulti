package com.generals.module.main.model.bean

/**
 * description ： TODO:类的作用
 * date : 2025/4/2 20:50
 */
data class PublicAuthorResponse(
    val `data`: List<PublicAuthorInfo>,
    val errorCode: Int,
    val errorMsg: String
)

data class PublicAuthorInfo(
    val id: Int,
    val name: String
)