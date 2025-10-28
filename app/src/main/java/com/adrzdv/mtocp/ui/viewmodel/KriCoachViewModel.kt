package com.adrzdv.mtocp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.domain.repository.KriCoachRepo
import com.adrzdv.mtocp.mapper.toDomain
import com.adrzdv.mtocp.mapper.toDto
import com.adrzdv.mtocp.ui.model.KriCoachDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KriCoachViewModel(
    private val kriRepo: KriCoachRepo
) : ViewModel() {

    private val _kriCoaches = MutableLiveData<List<KriCoachDto>>()
    private val _filteredCoaches = MutableLiveData<List<KriCoachDto>>()
    val filteredCoaches: LiveData<List<KriCoachDto>> = _filteredCoaches

    var searchString: String = ""

    init {
        loadKriCoaches()
    }

    fun loadKriCoaches() {
        viewModelScope.launch(Dispatchers.IO) {
            val doms = kriRepo.getAllKriCoaches().map { it.toDomain() }
            val dtos = doms.map { it.toDto() }
            withContext(Dispatchers.Main) {
                _kriCoaches.value = dtos
                _filteredCoaches.value = dtos
            }
        }
    }

    fun filterKriCoaches(str: String?) {
        searchString = str?.trim()?.lowercase() ?: ""

        _filteredCoaches.value = if (searchString.isNullOrEmpty()) {
            _kriCoaches.value
        } else {
            _kriCoaches.value?.filter { it ->
                it.number.contains(searchString)
            }
        }

    }
}