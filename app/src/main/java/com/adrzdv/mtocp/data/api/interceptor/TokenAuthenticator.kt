package com.adrzdv.mtocp.data.api.interceptor

import android.util.Log
import com.adrzdv.mtocp.data.api.AuthApi
import com.adrzdv.mtocp.data.model.auth.RefreshRequest
import com.adrzdv.mtocp.data.repository.UserDataStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.HttpException
import java.io.IOException

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

            var shouldClearTokens = false

            val newToken = runBlocking {
                try {
                    val refreshToken = userDataStorage.getRefreshToken()
                    if (refreshToken.isNullOrBlank()) {
                        shouldClearTokens = true
                        return@runBlocking null
                    }

                    val refreshResponse = authApiProvider().refresh(
                        RefreshRequest(refreshToken)
                    )

                    if (refreshResponse.accessToken.isNotBlank()) {
                        userDataStorage.saveAccessToken(refreshResponse.accessToken)
                        refreshResponse.accessToken
                    } else {
                        shouldClearTokens = true
                        null
                    }

                } catch (e: HttpException) {
                    shouldClearTokens = e.code() == 401 || e.code() == 403
                    Log.w("TokenAuthenticator", "Token refresh failed with HTTP ${e.code()}", e)
                    null
                } catch (e: IOException) {
                    Log.w("TokenAuthenticator", "Token refresh failed due to network error", e)
                    null
                } catch (e: Exception) {
                    Log.w("TokenAuthenticator", "Token refresh failed", e)
                    null
                }
            }

            return if (!newToken.isNullOrBlank()) {
                response.request.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
            } else {
                if (shouldClearTokens) {
                    userDataStorage.deleteToken()
                }
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
