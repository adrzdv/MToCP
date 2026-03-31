package com.adrzdv.mtocp.data.model

data class LoginResponse(
    val token: String?,
    val name: String?,
    val id: Int?,
    val secId: String?,
    val error: String?
)
