package com.adrzdv.mtocp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.util.RenderWakeUpService
import kotlinx.coroutines.launch

class RequestWebViewModel : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var isGettingNumber by mutableStateOf(false)
        private set

    var showNameDialog by mutableStateOf(false)
        private set

    var resultDialogText by mutableStateOf<String?>(null)
        private set

    var workerName by mutableStateOf("")
        private set

    private val wakeUpService = RenderWakeUpService()

    fun onWorkerNameChanged(newName: String) {
        workerName = newName
    }

    fun requestRender() {
        viewModelScope.launch {
            isLoading = true
            val success = wakeUpService.wakeUpRender()
            isLoading = false
            if (success) {
                showNameDialog = true
            } else {
                resultDialogText = "Ошибка подключения к Render"
            }
        }
    }

    fun getNumber() {
        viewModelScope.launch {
            showNameDialog = false
            isGettingNumber = true
            val number = wakeUpService.getNumber(workerName)
            isGettingNumber = false
            resultDialogText = number ?: "Ошибка получения номера"
        }
    }

    fun dismissDialogs() {
        showNameDialog = false
        resultDialogText = null
    }
}