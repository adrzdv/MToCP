package com.adrzdv.mtocp.ui.state

data class ChangePasswordState(
    val newPassword: String = "",
    val confirmNewPassword: String = "",
    val showPassword: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)