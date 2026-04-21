package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.repository.AuthRepository
import com.adrzdv.mtocp.ui.state.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    private val _regState = MutableStateFlow(AuthState())
    val regState = _regState.asStateFlow()

    fun onLoginChange(newLogin: String) {
        _regState.update { it.copy(login = newLogin, loginErrorRes = validateLogin(newLogin)) }
    }

    fun onPasswordChange(newPassword: String) {
        _regState.update {
            it.copy(
                password = newPassword,
                passwordErrorRes = validatePassword(newPassword)
            )
        }
    }

    fun login() {
        viewModelScope.launch {
            _regState.update { it.copy(isLoading = true, errorMessage = null) }

            val login = _regState.value.login
            val password = _regState.value.password

            val loginError = validateLogin(login)
            val passwordError = validatePassword(password)

            if (loginError != null || passwordError != null) {
                _regState.update {
                    it.copy(
                        loginErrorRes = loginError,
                        passwordErrorRes = passwordError,
                        isLoading = false
                    )
                }
                return@launch
            }

            try {
                val authResult = repository.login(login, password)
                if (authResult.isSuccess) {
                    authResult.token?.let { token ->
                        repository.saveToken(token)
                    }
                    authResult.name?.let { name ->
                        repository.saveUsername(name)
                    }
                    authResult.id?.let { id ->
                        repository.saveUserId(id)
                    }
                    authResult.secId?.let { secId ->
                        repository.saveUserSecId(secId)
                    }
                    _regState.update { it.copy(isLoading = false, isSuccess = true) }
                } else {
                    _regState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = authResult.errorMessage
                        )
                    }
                }
            } catch (e: Exception) {
                _regState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    private fun validateLogin(login: String): Int? {
        return when {
            login.isBlank() -> R.string.empty_string
            login.length < 8 -> R.string.login_length
            else -> null
        }
    }

    private fun validatePassword(password: String): Int? {
        return when {
            password.isBlank() -> R.string.empty_string
            password.length < 8 -> R.string.password_length
            else -> null
        }
    }
}