package com.generals.lib.base.util

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.StandardCharsets

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 打印请求
        val requestBody = request.body
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        val charset = StandardCharsets.UTF_8

        val mediaType = requestBody?.contentType()
        val bodyString = buffer.readString(charset)

        Log.d("zzx", "Sending request to ${request.url}")
        Log.d("zzx","Method: ${request.method}")
        Log.d("zzx", "Headers: ${request.headers}")
        Log.d("zzx", "Body: $bodyString")

        // 继续请求
        return chain.proceed(request)
    }
}