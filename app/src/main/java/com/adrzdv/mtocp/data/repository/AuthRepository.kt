package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.model.AuthResult

interface AuthRepository {
    suspend fun login(login: String, password: String): AuthResult
    fun saveToken(token: String)
    fun getToken(): String?
    fun saveUsername(username: String)
    fun saveUserId(id: Int)
    fun saveUserSecId(id: String)
    fun getUsername(): String?
    fun getUserId(): Int?
    fun getUserSecId(): String?
}