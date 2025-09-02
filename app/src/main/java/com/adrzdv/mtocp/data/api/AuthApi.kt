package com.adrzdv.mtocp.data.api

import com.adrzdv.mtocp.data.model.LoginRequest
import com.adrzdv.mtocp.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}