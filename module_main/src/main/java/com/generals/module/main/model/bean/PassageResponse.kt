package com.generals.module.main.model.bean

/**
 * description ： TODO:类的作用
 * date : 2025/4/1 20:46
 */
data class PassageResponse(
    val data: Data,
    val errorCode: Int,
    val errorMsg: String
)

data class Data(
    val curPage: Int,
    val datas: List<PassageInfo>,
    val pageCount: Int
)

data class PassageInfo(
    val author: String,
    val chapterName: String,
    val collect: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val shareUser: String,
    val title: String
)
