package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.model.AuthResult

interface AuthRepository {
    suspend fun login(login: String, password: String): AuthResult
    fun saveToken(token: String)
    fun getToken(): String?
}