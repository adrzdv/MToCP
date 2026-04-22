package com.adrzdv.mtocp.data.api.interceptor

import com.adrzdv.mtocp.data.model.auth.DeviceIdProvider
import okhttp3.Interceptor
import okhttp3.Response

class DeviceInterceptor(
    private val deviceIdProvider: DeviceIdProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val newRequest = request.newBuilder()
            .addHeader("X-Device-Id", deviceIdProvider.get())
            .build()

        return chain.proceed(newRequest)
    }
}