package com.adrzdv.mtocp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.data.model.ChangePasswordResult
import com.adrzdv.mtocp.data.repository.AuthRepository
import com.adrzdv.mtocp.ui.state.ChangePasswordState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChangePasswordBottomSheetViewModel(
    private val authRepository: AuthRepository,
    private val lengthError: String,
    private val digitError: String,
    private val upperCaseError: String,
    private val specDigitError: String,
    private val passwordNotMatchedError: String
) : ViewModel() {

    var state by mutableStateOf(ChangePasswordState())
        private set

    private val _result = MutableStateFlow<ChangePasswordResult?>(null)
    val result: StateFlow<ChangePasswordResult?> = _result

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val specCharList: List<Char> = listOf(
        '!',
        '@',
        '#',
        '$',
        '%',
        '^',
        '*',
        '-',
        '_',
        '+',
        '='
    )

    fun onPasswordChange(newPassword: String) {
        val hint = when {
            newPassword.length < 8 -> lengthError
            !newPassword.any(Char::isDigit) -> digitError
            !newPassword.any(Char::isUpperCase) -> upperCaseError
            !newPassword.any { it in specCharList } -> specDigitError
            else -> null
        }
        state = state.copy(newPassword = newPassword.trim(), passwordHint = hint, isError = false)
    }

    fun onConfirmChange(confirm: String) {
        state = state.copy(confirmNewPassword = confirm.trim(), isError = false)
    }

    fun togglePasswordVisibility() {
        state = state.copy(showPassword = !state.showPassword)
    }

    fun changePassword(): Boolean {
        if (!validate()) {
            Log.d("AuthRepo", "Password invalid")
            return false
        }

        viewModelScope.launch {
            _isLoading.value = true
            Log.d("AuthRepo", "IS LOADING: " + _isLoading.value)
            state = state.copy(isError = false, errorMessage = null)

            try {
                val res = authRepository.changePassword(state.newPassword)
                Log.d("AuthRepo", res.toString())
                _result.value = res
            } finally {
                _isLoading.value = false
                Log.d("AuthRepo", "IS LOADING: " + _isLoading.value)
            }
        }
        return true
    }

    private fun validate(): Boolean {
        val error = when {
            state.passwordHint != null -> state.passwordHint
            state.newPassword != state.confirmNewPassword -> passwordNotMatchedError
            else -> null
        }

        return if (error != null) {
            state = state.copy(isError = true, errorMessage = error)
            false
        } else true
    }
}