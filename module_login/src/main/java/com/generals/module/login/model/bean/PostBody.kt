package com.generals.module.login.model.bean

/**
 * description ： TODO:类的作用
 * date : 2025/3/28 09:49
 */
class PostBody() {
}

data class LoginBody(
    val username: String,
    val password: String
)
data class SignBody(val username: String, val password: String, val repassword: String)
