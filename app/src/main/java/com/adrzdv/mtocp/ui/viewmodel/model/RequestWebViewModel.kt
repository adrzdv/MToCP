package com.adrzdv.mtocp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.data.api.RetrofitClient
import com.adrzdv.mtocp.data.model.LogEntry
import com.adrzdv.mtocp.data.model.old.NameRequest
import kotlinx.coroutines.launch

class RequestWebViewModel : ViewModel() {

    private val api = RetrofitClient.docRequestApi

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

    var logs by mutableStateOf<List<LogEntry>>(emptyList())
        private set

    var isLogsLoading by mutableStateOf(false)
        private set

    fun setWorkerIfTokenExist(username: String) {
        workerName = username
    }

    fun onWorkerNameChanged(newName: String) {
        workerName = newName
    }

    fun requestRender() {
        viewModelScope.launch {
            isLoading = true

            try {
                if (workerName.isBlank()) {
                    showNameDialog = true
                } else {
                    getNumberByAuthUserInternal()
                }

                loadLastLogs()
            } catch (e: Exception) {
                resultDialogText = "Ошибка подключения к серверу"
                Log.d("RequestWebViewModel", e.message.toString())
            } finally {
                isLoading = false
            }
        }
    }

    fun getNumber() {
        viewModelScope.launch {
            showNameDialog = false
            isGettingNumber = true

            try {
                val response = api.getNumber(NameRequest(workerName))
                resultDialogText = response.number
            } catch (e: Exception) {
                resultDialogText = "Ошибка получения номера"
                Log.d("RequestWebViewModel", e.message.toString())
            } finally {
                isGettingNumber = false
            }
        }
    }

    fun dismissDialogs() {
        showNameDialog = false
        resultDialogText = null
    }

    fun loadLastLogs() {
        viewModelScope.launch {
            isLogsLoading = true

            try {
                logs = api.getLastLogs()
            } catch (e: Exception) {
                logs = emptyList()
                Log.d("RequestWebViewModel", e.message.toString())
            } finally {
                isLogsLoading = false
            }
        }
    }

    private suspend fun getNumberByAuthUserInternal() {
        isGettingNumber = true

        try {
            val response = api.getNumber(NameRequest(workerName))
            resultDialogText = response.number
        } catch (e: Exception) {
            resultDialogText = "Ошибка получения номера"
            Log.d("RequestWebViewModel", e.message.toString())
        } finally {
            isGettingNumber = false
        }
    }


}