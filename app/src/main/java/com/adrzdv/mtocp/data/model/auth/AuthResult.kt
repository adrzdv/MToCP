package com.adrzdv.mtocp.data.model.auth

import java.util.UUID

data class AuthResult(
    val isSuccess: Boolean,
    val profileId: UUID? = null,
    val userId: Int? = null,
    val fullName: String? = null,
    val secondaryId: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val role: String? = null,
    val branchName: String? = null,
    val errorMessage: String? = null
)