package com.adrzdv.mtocp.ui.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.Coach
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain
import com.adrzdv.mtocp.mapper.ViolationMapper
import okhttp3.internal.toImmutableMap
import java.time.LocalDateTime

class CoachViewModel(
    private val coachNumber: String,
    private val orderViewModel: OrderViewModel,
    private val depotViewModel: DepotViewModel,
) : ViewModel() {
    private val _coachMut
        get() = mutableStateOf(
            orderViewModel.collector?.objectsMap?.get(
                coachNumber
            )
        )
    private val _coach = _coachMut.value
    private val _violationMap = mutableStateMapOf<Int, ViolationDomain>()
    val violationMap: Map<Int, ViolationDomain> get() = _violationMap
    private val _worker = mutableStateOf((_coach as? PassengerCar)?.worker as? InnerWorkerDomain)
    val workerType: WorkerTypes? get() = _worker.value?.workerType

    init {
        _coach?.violationMap?.forEach { (code, violation) ->
            _violationMap[code] = violation
        }
    }

    fun getWorker(): InnerWorkerDomain? {
        return _coach?.worker as? InnerWorkerDomain
    }

    fun addTagToViolation(code: Int, tag: String) {
        _violationMap[code]?.attributeMap?.put("tag", tag)
    }

    fun changeAmount(code: Int, amount: Int) {
        _violationMap[code]?.amount = amount
    }

    fun cleanViolations() {
        _coach?.clearViolationMap()
        _violationMap.clear()
    }

    fun toggleViolationResolved(code: Int) {
        _violationMap[code]?.isResolved = _violationMap[code]?.isResolved == false

    }

    fun deleteViolation(code: Int) {
        _violationMap.remove(code)
    }

    fun saveCoachInOrder() {
        addViolationMap()
        orderViewModel.deleteRevisionObject(_coach)
        orderViewModel.addRevisionObject(_coach)
    }

    fun addWorker(
        id: Int,
        name: String,
        depotName: String,
        workerType: WorkerTypes
    ) {
        val depot = depotViewModel.getDepotDomain(depotName)
        val newWorker = InnerWorkerDomain(id, name, depot, workerType)
        _coach?.worker = newWorker
        _worker.value = newWorker
    }

    fun addWorker(worker: InnerWorkerDomain) {
        _coach?.worker = worker
        _worker.value = worker
    }

    fun addViolation(violation: ViolationDomain) {
        _coach?.addViolation(violation)
        _violationMap[violation.code] = violation
    }

    fun removeViolation(violation: ViolationDomain) {
        _coach?.deleteViolation(violation.code)
        _violationMap[violation.code] = violation
    }

    fun setStartRevisionTime(startTime: LocalDateTime) {
        if (_coach?.revisionDateStart == null) {
            _coach?.revisionDateStart = startTime
        }
    }

    fun setEndRevisionTime(endTime: LocalDateTime) {
        if (_coach?.revisionDateEnd == null) {
            _coach?.revisionDateEnd = endTime
        }
    }

    private fun addViolationMap() {
        _coach?.violationMap = _violationMap.toMutableMap()
    }

    fun reloadViolationsFromOrder() {
        _violationMap.clear()
        val coach = orderViewModel.collector?.objectsMap?.get(coachNumber)
        coach?.violationMap?.forEach { (code, violation) ->
            _violationMap[code] = violation
        }
    }
}