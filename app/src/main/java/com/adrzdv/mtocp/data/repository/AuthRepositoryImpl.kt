package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.api.AuthApi
import com.adrzdv.mtocp.data.model.AuthResult
import com.adrzdv.mtocp.data.model.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun login(
        login: String,
        password: String
    ): AuthResult =
        withContext(Dispatchers.IO) {
            try {
                val response = api.login(LoginRequest(login, password))

                if (response.token != null) {
                    AuthResult(isSuccess = true, token = response.token)
                } else {
                    AuthResult(isSuccess = false, errorMessage = response.error ?: "Unknown error")
                }
            } catch (e: Exception) {
                AuthResult(isSuccess = false, errorMessage = e.message ?: "Network error")
            }
        }

    override fun saveToken(token: String) {
        tokenStorage.saveToken(token)
    }

    override fun getToken(): String? = tokenStorage.getToken()
}