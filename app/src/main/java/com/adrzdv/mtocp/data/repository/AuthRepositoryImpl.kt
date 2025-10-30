package com.adrzdv.mtocp.data.repository

import android.util.Log
import com.adrzdv.mtocp.data.api.AuthApi
import com.adrzdv.mtocp.data.model.AuthResult
import com.adrzdv.mtocp.data.model.ChangePasswordResult
import com.adrzdv.mtocp.data.model.LoginRequest
import com.adrzdv.mtocp.data.model.PasswordRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val userDataStorage: UserDataStorage?
) : AuthRepository {

    override suspend fun login(
        login: String,
        password: String
    ): AuthResult =
        withContext(Dispatchers.IO) {
            try {
                val response = api.login(LoginRequest(login, password))

                if (response.token != null) {
                    AuthResult(
                        isSuccess = true,
                        token = response.token,
                        name = response.name ?: "",
                        id = response.id ?: 0,
                        secId = response.secId ?: ""
                    )
                } else {
                    AuthResult(isSuccess = false, errorMessage = response.error ?: "Unknown error")
                }
            } catch (e: Exception) {
                AuthResult(isSuccess = false, errorMessage = e.message ?: "Network error")
            }
        }

    override fun saveToken(token: String) {
        userDataStorage?.saveToken(token)
    }

    override fun getToken(): String? = userDataStorage?.getToken()

    override fun saveUsername(username: String) {
        userDataStorage?.saveUsername(username)
    }

    override fun saveUserId(id: Int) {
        userDataStorage?.saveUserId(id)
    }

    override fun saveUserSecId(id: String) {
        userDataStorage?.saveUserSecId(id)
    }

    override fun getUsername(): String? = userDataStorage?.getUsername()

    override fun getUserId(): Int? = userDataStorage?.getUserId()

    override fun getUserSecId(): String? = userDataStorage?.getUserSecId()

    override suspend fun changePassword(password: String): ChangePasswordResult =
        withContext(Dispatchers.IO) {
            try {
                val response = api.changePassword(PasswordRequest(password))
                val msg = listOfNotNull(
                    response.code(),
                    response.errorBody().toString()
                ).joinToString(",")
                Log.d("AuthRepo", msg)
                if (response.isSuccessful) {
                    ChangePasswordResult.Success
                } else {
                    val errMsg = response.errorBody()?.string()
                    ChangePasswordResult.ServerError(response.code(), errMsg)
                }
            } catch (e: Exception) {
                Log.d("AuthRepo", e.message.toString())
                ChangePasswordResult.NetworkError(e.message)
            }
        }
}