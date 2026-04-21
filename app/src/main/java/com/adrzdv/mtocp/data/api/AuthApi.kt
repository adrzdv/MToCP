package com.adrzdv.mtocp.data.api

import com.adrzdv.mtocp.data.model.auth.AuthRequest
import com.adrzdv.mtocp.data.model.auth.AuthResponse
import com.adrzdv.mtocp.data.model.old.PasswordRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

//    @Headers(
//        "X-Device-Id: application/json"
//    )
    @POST("/auth/login")
    suspend fun login(@Body body: AuthRequest): AuthResponse

    @POST("/auth/logout")
    suspend fun logout();

    @POST("password")
    suspend fun changePassword(@Body request: PasswordRequest): Response<Unit>
}