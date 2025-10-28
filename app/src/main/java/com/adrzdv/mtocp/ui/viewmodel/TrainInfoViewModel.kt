package com.adrzdv.mtocp.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.domain.repository.TrainRepository
import com.adrzdv.mtocp.mapper.TrainMapper
import com.adrzdv.mtocp.ui.model.TrainDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainInfoViewModel(
    private val trainRepo: TrainRepository
) : ViewModel() {

    private val _trains = mutableStateOf<List<TrainDto>>(emptyList())
    val trains: State<List<TrainDto>> get() = _trains

    private val _filteredTrains = mutableStateOf<List<TrainDto>>(emptyList())
    val filteredTrains: State<List<TrainDto>> get() = _filteredTrains

    private var searchString: String = ""

    fun loadTrains() {
        viewModelScope.launch(Dispatchers.IO) {
            val joinedData = trainRepo.trainsWithFullData
            val domData = joinedData.map { TrainMapper.fromTrainWithDepotAndBranchToDomain(it) }
            val dtoData = domData.map { TrainMapper.fromDomainToDto(it) }

            withContext(Dispatchers.Main) {
                _trains.value = dtoData
                _filteredTrains.value = dtoData
            }
        }
    }

    fun filterTrainListByString(str: String?) {
        searchString = str?.trim()?.lowercase() ?: ""

        _filteredTrains.value = if (searchString.isNullOrEmpty()) {
            _trains.value
        } else {
            _trains.value.filter { it ->
                it.number.lowercase().contains(searchString) ||
                        it.route.trim().lowercase().contains(searchString)
            }
        }
    }

}