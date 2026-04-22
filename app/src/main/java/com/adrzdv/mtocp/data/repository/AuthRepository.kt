package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.model.auth.AuthResult
import com.adrzdv.mtocp.data.model.old.ChangePasswordResult
import java.util.UUID

interface AuthRepository {
    suspend fun login(login: String, password: String): AuthResult
    fun saveAccessToken(token: String)
    fun getAccessToken(): String?
    fun saveRefreshToken(token: String)
    fun getRefreshToken(): String?
    fun saveUsername(username: String)
    fun saveUserId(id: Int)
    fun saveUserSecId(id: String)
    fun getUsername(): String?
    fun getUserId(): Int?
    fun getUserSecId(): String?
    fun saveProfileId(id: UUID)
    fun getProfileId(): UUID?
    fun saveUserRole(role: String)
    fun getUserRole(): String?
    fun saveUserBranch(branch: String)
    fun getUserBranch(): String?
    suspend fun refreshToken(refreshToken: String): AuthResult
    suspend fun changePassword(password: String): ChangePasswordResult
    suspend fun logout(refreshToken: String)
}