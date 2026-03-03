package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.domain.repository.BaseAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class AutocompleteViewModel(
    private val repository: BaseAppRepository<*, *>
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query
    private val _filteredItems = MutableStateFlow<List<String>>(emptyList())
    val filteredItems: StateFlow<List<String>> = _filteredItems

    fun onQueryChange(str: String) {
        _query.value = str
        viewModelScope.launch(Dispatchers.IO) { filterItems() }
    }

    private suspend fun filterItems() {
        if (_query.value.isBlank()) {
            _filteredItems.value = emptyList()
            return
        }
        _filteredItems.value = repository.getAll().stream()
            .map { it.toString() }
            .filter {
                it.lowercase(Locale.getDefault())
                    .contains(_query.value.lowercase(Locale.getDefault()))
            }
            .toList()
    }
}