package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.data.db.entity.ViolationFullInfo
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain
import com.adrzdv.mtocp.mapper.toDomain
import com.adrzdv.mtocp.mapper.toUi
import com.adrzdv.mtocp.ui.model.dto.ViolationUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ViolationViewModel(
    private val cacheRepository: CacheRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _revisionType = MutableStateFlow<RevisionType?>(null)
    val revisionType: StateFlow<RevisionType?> = _revisionType

    private val allEntities: List<ViolationFullInfo> =
        cacheRepository.violationCache.getAll()

    private val _items = MutableStateFlow<List<ViolationFullInfo>>(emptyList())
    val items: StateFlow<List<ViolationFullInfo>> = _items

    val suggestions: StateFlow<List<ViolationUi>> =
        _items
            .map { list -> list.map { it.toUi() } }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    init {
        observeFilters()
    }

    fun setRevisionType(type: RevisionType) {
        _revisionType.value = type
    }

    fun onQueryChange(str: String) {
        _query.value = str
    }

    fun resetQuery() {
        _query.value = ""
    }

    fun getDomainByCode(code: Int): ViolationDomain {
        return _items.value.first { it.violation.code == code }.toDomain()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeFilters() {

        viewModelScope.launch {
            combine(
                _query.debounce(300),
                _revisionType
            ) { query, type -> query to type }
                .mapLatest { (query, type) ->
                    allEntities
                        .asSequence()
                        .filter { info ->
                            type == null ||
                                    type == RevisionType.ALL ||
                                    info.revisionTypes.any { revisionTypeEntity ->
                                revisionTypeEntity.name.equals(
                                    type.revisionTypeTitle,
                                    ignoreCase = true
                                )
                            }
                        }
                        .filter { info ->
                            query.isBlank() ||
                                    info.violation.code == query.toIntOrNull() ||
                                    info.violation.description.contains(query, ignoreCase = true) ||
                                    info.violation.criteria.contains(query, ignoreCase = true) ||
                                    info.violation.measure.contains(query, ignoreCase = true) ||
                                    info.departments.any {
                                        it.shortName.contains(query, ignoreCase = true) ||
                                                it.fullName.contains(query, ignoreCase = true)
                                    } ||
                                    info.divisions.any {
                                        it.shortName.contains(query, ignoreCase = true) ||
                                                it.name.contains(query, ignoreCase = true)
                                    } ||
                                    info.revisionTypes.any {
                                        it.name.contains(query, ignoreCase = true)
                                    }
                        }
                        .toList()
                }
                .flowOn(Dispatchers.Default)
                .collect { filtered ->
                    _items.value = filtered
                }
        }
    }
}
