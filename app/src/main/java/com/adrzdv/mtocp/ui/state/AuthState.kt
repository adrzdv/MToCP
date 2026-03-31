package com.adrzdv.mtocp.ui.state

data class AuthState(
    val login: String = "",
    val password: String = "",
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val loginErrorRes: Int? = null,
    val passwordErrorRes: Int? = null
) {
    val isLoginValid: Boolean
        get() = login.isNotBlank()
    val isPasswordValid: Boolean
        get() = password.length >= 8
    val isFormValid: Boolean
        get() = isLoginValid && isPasswordValid
}