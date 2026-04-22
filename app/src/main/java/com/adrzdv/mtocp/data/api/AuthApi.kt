package com.adrzdv.mtocp.data.api

import com.adrzdv.mtocp.data.model.auth.AuthRequest
import com.adrzdv.mtocp.data.model.auth.AuthResponse
import com.adrzdv.mtocp.data.model.auth.RefreshRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth/login")
    suspend fun login(@Body body: AuthRequest): AuthResponse

    @POST("/auth/refresh")
    suspend fun refresh(@Body body: RefreshRequest): AuthResponse

    @POST("/auth/logout")
    suspend fun logout(@Body body: RefreshRequest)
}