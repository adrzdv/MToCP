package com.adrzdv.mtocp.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ServiceViewModel : ViewModel() {

    private val _snackMessage = MutableStateFlow<String?>(null)
    val snackMessage: StateFlow<String?> = _snackMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun showMessage(message: String) {
        _snackMessage.value = message
    }

    fun showErrorMessage(message: String) {
        _errorMessage.value = message
    }

    fun clearMessage() {
        _snackMessage.value = null
        _errorMessage.value = null
    }

}