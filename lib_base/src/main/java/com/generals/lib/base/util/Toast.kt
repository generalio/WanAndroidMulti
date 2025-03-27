package com.generals.lib.base.util

import android.content.Context
import android.widget.Toast
import com.generals.lib.base.BaseApp

/**
 * description ： Toast的封装
 * date : 2025/3/27 20:56
 */
class Toast {

    val context: Context = BaseApp.context

    fun String.showToast() {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }

    fun Int.showToast() {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }

}