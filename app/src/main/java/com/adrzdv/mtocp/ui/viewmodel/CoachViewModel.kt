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

    fun addTagToViolation(code: Int, tag: String) {
        _violationMap[code]?.attributeMap?.put("tag", tag)
    }

    fun changeAmount(code: Int, amount: Int) {
        _violationMap[code]?.amount = amount
    }

    fun cleanViolations() {
        _violationMap.clear()
    }

    fun toggleViolationResolved(code: Int) {
        if (_violationMap[code]?.isResolved == false) {
            _violationMap[code]?.isResolved = true
        } else {
            _violationMap[code]?.isResolved = false
        }

    }

    fun deleteViolation(code: Int) {
        _violationMap.remove(code)
    }

    fun saveCoachInOrder() {
        addViolationMap()
        orderViewModel.addRevisionObject(_coach)
    }

    fun addWorker(
        id: Int,
        name: String,
        depotName: String
    ) {
        val depot = depotViewModel.getDepotDomain(depotName)
        val newWorker: InnerWorkerDomain = InnerWorkerDomain(id, name, depot, workerType)
        _coach?.worker = newWorker
        _worker.value = newWorker
    }

    fun addViolation(violation: ViolationDomain) {
        _coach?.addViolation(violation)
        _violationMap[violation.code] = violation
    }

    fun removeViolation(violation: ViolationDomain) {
        _coach?.deleteViolation(violation.code)
        _violationMap[violation.code] = violation
    }

    fun addWorker(worker: InnerWorkerDomain) {
        _coach?.worker = worker
        _worker.value = worker
    }

    fun setStartRevisionTime(startTime: LocalDateTime) {
        _coach?.revisionDateStart = startTime
    }

    fun setEndRevisionTime(endTime: LocalDateTime) {
        _coach?.revisionDateEnd = endTime
    }

    private fun addViolationMap() {
        _coach?.violationMap = _violationMap.toMap()
    }

    fun reloadViolationsFromOrder() {
        _violationMap.clear()
        val coach = orderViewModel.collector?.objectsMap?.get(coachNumber)
        coach?.violationMap?.forEach { (code, violation) ->
            _violationMap[code] = violation
        }
    }
}