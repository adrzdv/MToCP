package com.adrzdv.mtocp.data.repository

import android.content.SharedPreferences

class TokenStorage(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_TOKEN = "mtocp_token"
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? = sharedPreferences.getString(KEY_TOKEN, null)
}