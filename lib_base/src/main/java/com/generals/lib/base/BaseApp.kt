package com.generals.lib.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * description ： 全局Application
 * date : 2025/3/27 20:22
 */
class BaseApp : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}