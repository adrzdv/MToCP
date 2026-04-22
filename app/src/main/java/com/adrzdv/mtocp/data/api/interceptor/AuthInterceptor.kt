package com.adrzdv.mtocp.data.api.interceptor

import com.adrzdv.mtocp.data.repository.UserDataStorage
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val userDataStorage: UserDataStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = userDataStorage.getAccessToken()

        val request = chain.request().newBuilder().apply {
            if (!token.isNullOrBlank()) {
                header("Authorization", "Bearer $token")
            }
        }.build()

        return chain.proceed(request)
    }
}
