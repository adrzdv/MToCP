package com.adrzdv.mtocp.data.model

data class AuthResult(
    val isSuccess: Boolean,
    val token: String? = null,
    val errorMessage: String? = null
)