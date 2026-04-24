package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.data.mapper.toDepartmentRefs
import com.adrzdv.mtocp.data.mapper.toDivisionRefs
import com.adrzdv.mtocp.data.mapper.toEntity
import com.adrzdv.mtocp.data.mapper.toTypeRefs
import com.adrzdv.mtocp.data.repository.DictionaryRepository
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import com.adrzdv.mtocp.domain.repository.ViolationRepository
import com.adrzdv.mtocp.domain.usecase.LoadAllViolationsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ServiceViewModel(
    private val loadAllViolationsUseCase: LoadAllViolationsUseCase,
    private val dictionaryRepository: DictionaryRepository,
    private val violationRepository: ViolationRepository,
    private val cacheRepository: CacheRepository
) : ViewModel() {

    private val _snackMessage = MutableStateFlow<String?>(null)
    val snackMessage: StateFlow<String?> = _snackMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

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

    fun syncDictionaries() {
        if (_isSyncing.value) return

        viewModelScope.launch {
            _isSyncing.value = true

            try {
                val api = loadAllViolationsUseCase.dictionaryApi

                val branches = loadPaged { page, size -> api.getBranches(page, size) }
                    .map { it.toEntity() }
                val departments = api.getDepartments().map { it.toEntity() }
                val depots = loadPaged { page, size -> api.getDepots(page, size) }
                    .map { it.toEntity() }
                val divisions = loadPaged { page, size -> api.getDivisions(page, size) }
                    .map { it.toEntity() }
                val revisionTypes = api.getRevisionTypes().map { it.toEntity() }
                val violations = loadAllViolationsUseCase.invoke()

                violationRepository.clearAllViolationData()

                dictionaryRepository.syncDictionaries(
                    newBranches = branches,
                    newDepartments = departments,
                    newDepots = depots,
                    newDivisions = divisions,
                    newRevisionTypes = revisionTypes
                )

                violationRepository.syncViolations(
                    violations = violations.map { it.toEntity() },
                    departmentLinks = violations.flatMap { it.toDepartmentRefs() },
                    divisionLinks = violations.flatMap { it.toDivisionRefs() },
                    typeLinks = violations.flatMap { it.toTypeRefs() }
                )

                cacheRepository.loadCache()
                showMessage("Справочники успешно обновлены")
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                showErrorMessage(error.message ?: "Не удалось обновить справочники")
            } finally {
                _isSyncing.value = false
            }
        }
    }

    private suspend fun <T> loadPaged(
        pageSize: Int = 1000,
        loader: suspend (page: Int, size: Int) -> List<T>
    ): List<T> {
        val result = mutableListOf<T>()
        var page = 0

        while (true) {
            val chunk = loader(page, pageSize)
            if (chunk.isEmpty()) break

            result += chunk
            if (chunk.size < pageSize) break

            page++
        }

        return result
    }
}
