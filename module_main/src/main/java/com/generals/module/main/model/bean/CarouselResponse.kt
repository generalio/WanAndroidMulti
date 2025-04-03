package com.generals.module.main.model.bean

/**
 * description ： TODO:类的作用
 * date : 2025/4/1 16:57
 */
data class CarouselResponse(
    val data: List<CarouselInfo>,
    val errorCode: Int,
    val errorMsg: String
)

data class CarouselInfo(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)