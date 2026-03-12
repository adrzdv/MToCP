package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.domain.repository.BaseAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class AutocompleteViewModel<V>(
    private val repository: BaseAppRepository<V, *>,
    private val searchSelector: ((V) -> String)
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query
    private val _filteredItems = MutableStateFlow<List<V>>(emptyList())
    val filteredItems: StateFlow<List<V>> = _filteredItems
    val suggestions: StateFlow<List<String>> =
        _filteredItems
            .map { list ->
                list.map { searchSelector(it) }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    init {
        observeQuery()
    }

    fun onQueryChange(str: String) {
        _query.value = str
    }

    fun resetQuery() {
        _query.value = ""
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeQuery() {
        viewModelScope.launch {
            _query
                .debounce(300)
                .distinctUntilChanged()
                .mapLatest { query ->
                    if (query.isBlank()) {
                        emptyList()
                    } else {
                        repository.getAll()
                            .filter {
                                searchSelector(it).contains(query, ignoreCase = true)
                            }
//                            .map { it.toString() }
//                            .filter {
//                                it.contains(query, ignoreCase = true)
//                            }
                    }
                }
                .flowOn(Dispatchers.Default)
                .collect { result ->
                    _filteredItems.value = result
                }
        }
    }
}