package com.adrzdv.mtocp.data.model

data class AuthResult(
    val isSuccess: Boolean,
    val token: String? = null,
    val name: String? = null,
    val id: Int? = null,
    val secId: String? = null,
    val errorMessage: String? = null
)