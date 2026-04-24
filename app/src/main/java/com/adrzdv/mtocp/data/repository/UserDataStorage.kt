package com.adrzdv.mtocp.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.UUID

class UserDataStorage(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_PROFILE_ID = "profile_id"
        private const val KEY_USER_ROLE = "user_role"
        private const val KEY_USER_BRANCH = "user_branch"
        private const val KEY_USERNAME = "username"
        private const val KEY_USERID = "user_id"
        private const val KEY_USERSECID = "user_sec_id"
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit { putString(KEY_ACCESS_TOKEN, token) }
    }

    fun getAccessToken(): String? = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    fun saveRefreshToken(token: String) {
        sharedPreferences.edit { putString(KEY_REFRESH_TOKEN, token) }
    }

    fun getRefreshToken(): String? = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)

    fun saveProfileId(id: UUID) {
        sharedPreferences.edit { putString(KEY_PROFILE_ID, id.toString()) }
    }

    fun getProfileId(): String? = sharedPreferences.getString(KEY_PROFILE_ID, null)

    fun saveUserRole(role: String) {
        sharedPreferences.edit { putString(KEY_USER_ROLE, role) }
    }

    fun getUserRole(): String? = sharedPreferences.getString(KEY_USER_ROLE, null)

    fun saveUserBranch(branch: String) {
        sharedPreferences.edit { putString(KEY_USER_BRANCH, branch) }
    }

    fun getUserBranch(): String? = sharedPreferences.getString(KEY_USER_BRANCH, null)

    fun saveUsername(username: String) {
        sharedPreferences.edit { putString(KEY_USERNAME, username) }
    }

    fun getUsername(): String? = sharedPreferences.getString(KEY_USERNAME, null)

    fun saveUserId(id: Int) {
        sharedPreferences.edit { putInt(KEY_USERID, id) }
    }

    fun getUserId(): Int? = sharedPreferences.getInt(KEY_USERID, 0)

    fun saveUserSecId(userSecId: String) {
        sharedPreferences.edit { putString(KEY_USERSECID, userSecId) }
    }

    fun getUserSecId(): String? = sharedPreferences.getString(KEY_USERSECID, null)

    fun deleteToken() {
        sharedPreferences.edit {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
            remove(KEY_PROFILE_ID)
            remove(KEY_USER_ROLE)
            remove(KEY_USER_BRANCH)
            remove(KEY_USERNAME)
            remove(KEY_USERID)
            remove(KEY_USERSECID)
        }
    }
}