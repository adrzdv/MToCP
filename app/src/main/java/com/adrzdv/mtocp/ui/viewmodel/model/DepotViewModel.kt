package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import com.adrzdv.mtocp.domain.model.departments.BranchDomain
import com.adrzdv.mtocp.domain.model.departments.DepotDomain
import com.adrzdv.mtocp.ui.model.DepotDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.collectLatest

class DepotViewModel(
    private val cacheRepository: CacheRepository
) : ViewModel() {

    private val allDepots = MutableStateFlow<List<DepotWithBranch>>(emptyList())
    private val query = MutableStateFlow("")
    private val isDinner = MutableStateFlow(false)
    private val filteredDepotList = MutableLiveData<List<DepotDto>>(emptyList())

    val filteredDepots: LiveData<List<DepotDto>> = filteredDepotList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (cacheRepository.depotCache.isEmpty()) {
                cacheRepository.loadCache()
            }
            allDepots.value = cacheRepository.depotCache.getAll()
            applyFilter()
        }

        observeFilters()
    }

    fun filterByString(string: String?) {
        query.value = string?.trim().orEmpty()
    }

    fun filterDinner() {
        isDinner.value = true
    }

    fun resetDinnerFilter() {
        isDinner.value = false
    }

    fun getDepotDomain(depotName: String): DepotDomain {
        return allDepots.value
            .firstOrNull { depot ->
                depot.depot.fullName.equals(depotName, ignoreCase = true) ||
                        depot.depot.shortName.equals(depotName, ignoreCase = true)
            }
            ?.toDomain()
            ?: throw IllegalArgumentException("Depot $depotName not found")
    }

    @OptIn(FlowPreview::class)
    private fun observeFilters() {
        viewModelScope.launch(Dispatchers.Default) {
            query
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest {
                    applyFilter()
                }
        }
    }

    private fun applyFilter() {
        val search = query.value.lowercase()
        val dinnerOnly = isDinner.value

        val result = allDepots.value
            .asSequence()
            .filter { depot ->
                if (!dinnerOnly) {
                    true
                } else {
                    // The dinner-depot flag is absent in the new schema for now.
                    true
                }
            }
            .filter { depot ->
                search.isBlank() ||
                        depot.depot.fullName.lowercase().contains(search) ||
                        depot.depot.shortName.lowercase().contains(search) ||
                        depot.branch.fullName.lowercase().contains(search) ||
                        depot.branch.shortName.lowercase().contains(search) ||
                        depot.depot.phoneNumber.lowercase().contains(search)
            }
            .map { depot ->
                DepotDto(
                    depot.depot.fullName,
                    depot.depot.shortName,
                    depot.depot.phoneNumber,
                    depot.branch.fullName,
                    depot.branch.shortName
                )
            }
            .toList()

        filteredDepotList.postValue(result)
    }

    private fun DepotWithBranch.toDomain(): DepotDomain {
        return DepotDomain(
            depot.id,
            depot.fullName,
            depot.shortName,
            depot.phoneNumber,
            BranchDomain(
                branch.id,
                branch.fullName,
                branch.shortName
            ),
            depot.isActive,
            null
        )
    }
}
