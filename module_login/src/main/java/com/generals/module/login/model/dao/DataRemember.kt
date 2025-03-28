package com.generals.module.login.model.dao

import android.content.Context
import androidx.core.content.edit
import com.generals.lib.base.BaseApp

/**
 * description ： TODO:类的作用
 * date : 2025/3/28 21:10
 */
object DataRemember {

    fun rememberPassword(username: String, password: String) {
        BaseApp.context.getSharedPreferences("userData", Context.MODE_PRIVATE).edit {
            putString("username", username)
            putString("password", password)
        }
    }

    fun getPassword() : Pair<String, String> {
        val sharedPreferences = BaseApp.context.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()
        val password = sharedPreferences.getString("password", "").toString()
        return Pair(username, password)
    }

}