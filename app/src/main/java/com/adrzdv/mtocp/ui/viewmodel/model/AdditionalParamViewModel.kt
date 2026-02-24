package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.domain.model.violation.StaticsParam
import com.adrzdv.mtocp.domain.model.violation.TempParametersDomain
import com.adrzdv.mtocp.domain.repository.TempParamRepository
import com.adrzdv.mtocp.mapper.StaticsParametersMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdditionalParamViewModel(
    private val tempParamsRepo: TempParamRepository
) : ViewModel() {
    private var _tempParams = mutableStateListOf<StaticsParam>()
    private var _tempDomainParams = mutableStateListOf<TempParametersDomain>()
    val tempParams: List<StaticsParam> = _tempParams
    private val _mapParams = mutableMapOf<String, StaticsParam>()
    val mapParams = _mapParams

    fun loadTrainParams() {
        viewModelScope.launch {
            loadData()
            filterParamsByTrain(true)
            mapParams()
        }
    }

    fun loadCoachParams() {
        viewModelScope.launch {
            loadData()
            filterParamsByTrain(false)
            mapParams()
        }
    }

    fun updateCompleted(id: Int, completed: Boolean) {
        val index = _tempParams.indexOfFirst { it.id == id }
        if (index != -1) {
            _tempParams[index].completed = completed
        }
    }

    fun updateNote(id: Int, note: String) {
        val index = _tempParams.indexOfFirst { it.id == id }
        if (index != -1) {
            _tempParams[index].note = note
        }
    }

    fun getMapOfParams() {
        for (param in _tempParams) {
            if (param.completed == null) param.completed = true
            _mapParams[param.name] = param
        }
    }

    fun setParams(params: List<StaticsParam>) {
        _tempParams.clear()
        _tempParams.addAll(params)
    }

    private suspend fun loadData() {
        val params = withContext(Dispatchers.IO) {
            tempParamsRepo.all
        }
        val domains = params.map { StaticsParametersMapper.fromEntityToDomain(it) }
        _tempDomainParams.clear()
        _tempDomainParams.addAll(domains)
    }

    private fun filterParamsByTrain(isTrain: Boolean) {

        val filtered: List<TempParametersDomain> = if (isTrain) {
            _tempDomainParams.filter { param -> param.train }
        } else {
            _tempDomainParams.filter { param -> !param.train }
        }

        _tempDomainParams.clear()
        _tempDomainParams.addAll(filtered)
    }

    private fun mapParams() {
        _tempParams.clear()
        _tempParams.addAll(
            _tempDomainParams.map { domainParam ->
                StaticsParametersMapper.fromDomainToShort(domainParam)
            }
        )
    }
}