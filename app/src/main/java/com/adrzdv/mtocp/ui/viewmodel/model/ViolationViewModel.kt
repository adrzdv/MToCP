package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.data.db.entity.ViolationEntity
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain
import com.adrzdv.mtocp.mapper.ViolationMapper
import com.adrzdv.mtocp.mapper.toUi
import com.adrzdv.mtocp.ui.model.statedtoui.ViolationUi
import kotlinx.coroutines.Dispatchers
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

    private val allEntities: List<ViolationEntity> =
        cacheRepository.violationCache.getAll()

    private val _items = MutableStateFlow<List<ViolationDomain>>(emptyList())
    val items: StateFlow<List<ViolationDomain>> = _items

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
        return _items.value.first { it.code == code }
    }

    @OptIn(FlowPreview::class)
    private fun observeFilters() {

        viewModelScope.launch {
            combine(
                _query.debounce(300),
                _revisionType
            ) { query, type -> query to type }
                .mapLatest { (query, type) ->
                    allEntities
                        .asSequence()
                        .filter { entity ->
                            val typeMatch = when (type) {
                                RevisionType.IN_TRANSIT -> entity.inTransit
                                RevisionType.AT_START_POINT -> entity.atStartPoint
                                RevisionType.AT_TURNROUND_POINT -> entity.atTurnroundPoint
                                RevisionType.AT_TICKET_OFFICE -> entity.atTicketOffice
                                else -> true
                            }
                            typeMatch
                        }
                        .map { ViolationMapper.fromEntityToDomain(it) }
                        .filter { domain ->
                            query.isBlank() ||
                                    domain.code == query.toIntOrNull() ||
                                    domain.name.contains(query, ignoreCase = true)
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