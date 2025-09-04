package com.adrzdv.mtocp.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit

class UserDataStorage(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_TOKEN = "mtocp_token"
        private const val KEY_USERNAME = "username"
        private const val KEY_USERID = "user_id"
        private const val KEY_USERSECID = "user_sec_id"
    }

    fun saveToken(token: String) {
        sharedPreferences.edit { putString(KEY_TOKEN, token) }
    }

    fun getToken(): String? = sharedPreferences.getString(KEY_TOKEN, null)

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
}