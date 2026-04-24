package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.data.model.document.DocumentUserDataDto
import com.adrzdv.mtocp.data.repository.DocumentRepository
import kotlinx.coroutines.launch

class RequestDocumentViewModel(
    private val repository: DocumentRepository
) : ViewModel() {

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

    var workerPrefix by mutableStateOf("")
        private set

    var logs by mutableStateOf<List<DocumentUserDataDto>>(emptyList())
        private set

    var isLogsLoading by mutableStateOf(false)
        private set

    var isEndReached by mutableStateOf(false)
        private set

    private var currentPage = 0
    private val pageSize = 20

    fun setWorkerIfTokenExist(username: String) {
        workerName = username
    }

    fun setWorkerPrefixIfExist(prefix: String) {
        workerPrefix = prefix
    }

    fun onWorkerNameChanged(newName: String) {
        workerName = newName
    }

    fun requestList() {
        viewModelScope.launch {
            isLoading = true

            try {
                if (workerName.isBlank()) {
                    showNameDialog = true
                } else {
                    createDocumentInternal()
                }

                loadFirstPage()

            } catch (e: Exception) {
                resultDialogText = "Ошибка подключения к серверу"
            } finally {
                isLoading = false
            }
        }
    }

    fun createDocument() {
        viewModelScope.launch {
            showNameDialog = false
            createDocumentInternal()
            loadFirstPage()
        }
    }

    private suspend fun createDocumentInternal() {
        isGettingNumber = true

        try {
            val number = repository.createDocument(workerName)
            resultDialogText = number
        } catch (e: Exception) {
            resultDialogText = "Ошибка получения номера"
        } finally {
            isGettingNumber = false
        }
    }

    fun loadFirstPage() {
        currentPage = 0
        isEndReached = false
        logs = emptyList()
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLogsLoading || isEndReached) return

        viewModelScope.launch {
            isLogsLoading = true

            try {
                val response = repository.getDocumentsPage(
                    page = currentPage,
                    size = pageSize,
                    prefix = workerPrefix
                )

                logs = logs + response.entities

                isEndReached = logs.size >= response.total
                currentPage++

            } catch (e: Exception) {
                resultDialogText = "Ошибка загрузки списка"
            } finally {
                isLogsLoading = false
            }
        }
    }

    fun dismissDialogs() {
        showNameDialog = false
        resultDialogText = null
    }
}