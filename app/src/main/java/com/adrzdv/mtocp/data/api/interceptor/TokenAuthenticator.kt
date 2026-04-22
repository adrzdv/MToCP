package com.adrzdv.mtocp.data.api.interceptor

import com.adrzdv.mtocp.data.api.AuthApi
import com.adrzdv.mtocp.data.model.auth.RefreshRequest
import com.adrzdv.mtocp.data.repository.UserDataStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val userDataStorage: UserDataStorage,
    private val authApiProvider: () -> AuthApi
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 3) return null

        synchronized(this) {

            val currentToken = userDataStorage.getAccessToken()
            val requestToken = response.request.header("Authorization")
                ?.removePrefix("Bearer ")

            if (currentToken != null && currentToken != requestToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentToken")
                    .build()
            }

            val newToken = runBlocking {
                try {
                    val refreshToken = userDataStorage.getRefreshToken() ?: return@runBlocking null

                    val refreshResponse = authApiProvider().refresh(
                        RefreshRequest(refreshToken)
                    )

                    if (refreshResponse.accessToken.isNotBlank()) {
                        userDataStorage.saveAccessToken(refreshResponse.accessToken)
                        refreshResponse.accessToken
                    } else null

                } catch (e: Exception) {
                    null
                }
            }

            return if (!newToken.isNullOrBlank()) {
                response.request.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
            } else {
                userDataStorage.deleteToken()
                null
            }
        }
    }

    private fun responseCount(response: Response): Int {
        var result = 1
        var r = response.priorResponse
        while (r != null) {
            result++
            r = r.priorResponse
        }
        return result
    }
}