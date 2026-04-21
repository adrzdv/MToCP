package com.adrzdv.mtocp.data.model.auth

import java.util.UUID

data class AuthResponse(
    val profileId: UUID,
    val userId: Int,
    val fullName: String,
    val secondaryId: String,
    val accessToken: String,
    val refreshToken: String,
    val role: String,
    val branchName: String
)
