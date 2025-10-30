package com.adrzdv.mtocp.data.api

import com.adrzdv.mtocp.data.model.LoginRequest
import com.adrzdv.mtocp.data.model.LoginResponse
import com.adrzdv.mtocp.data.model.PasswordRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("password")
    suspend fun changePassword(@Body request: PasswordRequest): Response<Unit>
}