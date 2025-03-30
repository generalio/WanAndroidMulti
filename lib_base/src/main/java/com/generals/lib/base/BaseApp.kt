package com.generals.lib.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter

/**
 * description ： 全局Application
 * date : 2025/3/27 20:22
 */
class BaseApp : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context
    }

    private val isDebug = true

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        if(isDebug) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}