package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.api.AuthApi
import com.adrzdv.mtocp.data.model.auth.AuthRequest
import com.adrzdv.mtocp.data.model.auth.AuthResult
import com.adrzdv.mtocp.data.model.old.ChangePasswordResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

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
                val response = api.login(AuthRequest(login, password))

                if (response.accessToken != null) {
                    AuthResult(
                        isSuccess = true,
                        profileId = response.profileId,
                        userId = response.userId,
                        fullName = response.fullName,
                        secondaryId = response.secondaryId,
                        accessToken = response.accessToken,
                        refreshToken = response.refreshToken,
                        role = response.role,
                        branchName = response.branchName
                    )

                } else {
                    AuthResult(isSuccess = false, errorMessage = "Unknown error")
                }
            } catch (e: Exception) {
                AuthResult(isSuccess = false, errorMessage = e.message ?: "Network error")
            }
        }

    override fun saveAccessToken(token: String) {
        userDataStorage?.saveAccessToken(token)
    }

    override fun getAccessToken(): String? = userDataStorage?.getAccessToken()
    override fun saveRefreshToken(token: String) {
        userDataStorage?.saveRefreshToken(token)
    }

    override fun getRefreshToken(): String? = userDataStorage?.getRefreshToken()

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

    override fun saveProfileId(id: UUID) {
        userDataStorage?.saveProfileId(id)
    }

    override fun getProfileId(): UUID = UUID.fromString(userDataStorage?.getProfileId())

    override fun saveUserRole(role: String) {
        userDataStorage?.saveUserRole(role)
    }

    override fun getUserRole(): String? = userDataStorage?.getUserRole()

    override fun saveUserBranch(branch: String) {
        userDataStorage?.saveUserBranch(branch)
    }

    override fun getUserBranch(): String? = userDataStorage?.getUserBranch()

    override suspend fun changePassword(password: String): ChangePasswordResult {
        TODO("Not supported in new version")
    }

//    override suspend fun changePassword(password: String): ChangePasswordResult =
//        withContext(Dispatchers.IO) {
//            try {
//                val response = api.changePassword(PasswordRequest(password))
//                val msg = listOfNotNull(
//                    response.code(),
//                    response.errorBody().toString()
//                ).joinToString(",")
//                Log.d("AuthRepo", msg)
//                if (response.isSuccessful) {
//                    ChangePasswordResult.Success
//                } else {
//                    val errMsg = response.errorBody()?.string()
//                    ChangePasswordResult.ServerError(response.code(), errMsg)
//                }
//            } catch (e: Exception) {
//                Log.d("AuthRepo", e.message.toString())
//                ChangePasswordResult.NetworkError(e.message)
//            }
//        }
}