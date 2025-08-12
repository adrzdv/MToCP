package com.adrzdv.mtocp.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.room.util.copy
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain
import com.adrzdv.mtocp.domain.usecase.DeleteViolationPhotoUseCase
import com.adrzdv.mtocp.ui.state.DinnerCoachState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.domain.model.violation.StaticsParam
import com.adrzdv.mtocp.domain.model.workers.OuterWorkerDomain
import com.adrzdv.mtocp.ui.activities.CameraActivity
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class DinnerCoachViewModel(
    private val initDinner: DinnerCar,
    private val onSave: (DinnerCar) -> Unit,
    private val deleteViolationPhotoUseCase: DeleteViolationPhotoUseCase
) : ViewModel() {
    private val _state = mutableStateOf(
        DinnerCoachState(
            idWorker = initDinner.worker?.id?.toString() ?: "",
            nameWorker = initDinner.worker?.name,
            typeWorker = initDinner.worker?.workerType?.description,
            violations = initDinner.violationMap
        )
    )

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()
    val state: State<DinnerCoachState> = _state

    fun getDisplayViolations(): List<ViolationDomain> {
        return _state.value.violations?.values?.toList() ?: mutableListOf()
    }

    fun onIdChange(newId: String, errorMessage: String) {
        _state.value = _state.value.copy(
            idWorker = newId,
            idError = when {
                newId.isEmpty() || newId.toIntOrNull() == null -> errorMessage
                else -> null
            }
        )
    }

    fun onTypeChange(newType: String, errorMessage: String) {
        _state.value = _state.value.copy(
            typeWorker = newType,
            typeError = when {
                newType.isEmpty() -> errorMessage
                else -> null
            }
        )
    }

    fun onNameChange(newName: String, errorMessage: String) {
        _state.value = _state.value.copy(
            nameWorker = newName,
            nameError = when {
                newName.isEmpty() -> errorMessage
                else -> null
            }
        )
    }

    fun addViolation(violation: ViolationDomain) {
        val updatedMap = _state.value.violations?.toMutableMap()
        updatedMap?.set(violation.code, violation)
        updateState(
            violations = updatedMap
        )
    }

    fun addAdditionalParams(statparams: Map<String, StaticsParam>) {
        _state.value = _state.value.copy(
            statParams = statparams
        )
    }

    fun addTagToViolation(code: Int, tag: String) {
        _state.value = _state.value.copy(
            violations = _state.value.violations?.toMutableMap()
                .apply {
                    this?.get(code)?.attributeMap?.set(tag, tag)
                }
        )
    }

    fun cleanViolations(orderNumber: String) {
        val updatedMap = _state.value.violations?.toMutableMap()
        for (violation in updatedMap?.values!!) {
            deletePhotoViolation(violation.code, initDinner.number, orderNumber)
        }
        updatedMap.clear()
        updateState(violations = updatedMap)
    }

    fun deleteViolation(code: Int, orderNumber: String) {
        val updatedMap = _state.value.violations?.toMutableMap()
        deletePhotoViolation(code, initDinner.number, orderNumber)
        updatedMap?.remove(code)
        updateState(
            violations = updatedMap
        )
    }

    fun updateViolationValue(code: Int, value: Int) {
        _state.value = _state.value.copy(
            violations = _state.value.violations?.toMutableMap()
                .apply { this?.get(code)?.amount = value }
        )
    }

    fun toggleResolvedViolation(code: Int) {
        _state.value = _state.value.copy(
            violations = _state.value.violations?.toMutableMap()
                .apply {
                    this?.get(code)?.let {
                        it.isResolved = !it.isResolved
                    }
                }
        )
    }

    fun setInputsEmpty() {
        _state.value = _state.value.copy(
            idWorker = "",
            nameWorker = "",
            typeWorker = "",
        )
    }

    fun onCameraResult(result: ActivityResult) {
        val message = if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getStringExtra("result") ?: MessageCodes.PHOTO_SUCCESS.errorTitle
        } else {
            result.data?.getStringExtra("result") ?: MessageCodes.PHOTO_ERROR.errorTitle
        }
        _snackbarMessage.value = message
    }

    fun snackbarShown() {
        _snackbarMessage.value = null
    }

    fun buildCameraIntent(context: Context): Intent {
        return Intent(context, CameraActivity::class.java)
    }

    fun onSave() {
        val current = _state.value
        val idValid = current.idWorker != null && current.idWorker.toIntOrNull() != null
        val nameValid = !current.nameWorker.isNullOrBlank()
        val typeValid = !current.typeWorker.isNullOrBlank()

        if (!idValid ||
            !nameValid ||
            !typeValid
        ) {
            updateState(
                idError = if (!idValid) MessageCodes.EMPTY_STRING.errorTitle else null,
                nameError = if (!nameValid) MessageCodes.EMPTY_STRING.errorTitle else null,
                typeError = if (!typeValid) MessageCodes.EMPTY_STRING.errorTitle else null
            )
            return
        }



        viewModelScope.launch {
            current.idWorker?.let { id ->
                current.nameWorker?.let { name ->
                    current.typeWorker?.let { type ->
                        current.violations?.let { currViolations ->
                            current.statParams?.let { currParams ->
                                val workerType = WorkerTypes.entries.find { it.description == type }
                                val worker = when {
                                    (initDinner.companyDomain != null) -> OuterWorkerDomain(
                                        id.toInt(),
                                        name,
                                        initDinner.companyDomain,
                                        workerType
                                    )

                                    else -> InnerWorkerDomain(
                                        id.toInt(),
                                        name,
                                        initDinner.depot,
                                        workerType
                                    )
                                }

                                val updatedCoach = DinnerCar(initDinner.number).apply {
                                    this.worker = worker
                                    this.type = initDinner.type
                                    this.revisionDateStart = initDinner.revisionDateStart
                                    if (initDinner.revisionDateEnd == null) {
                                        this.revisionDateEnd = LocalDateTime.now()
                                    } else {
                                        this.revisionDateEnd = initDinner.revisionDateEnd
                                    }
                                    this.companyDomain = initDinner.companyDomain
                                    this.depot = initDinner.depot
                                    this.coachRoute = initDinner.coachRoute
                                    this.additionalParams = currParams
                                    this.violationMap = currViolations
                                }
                                onSave(updatedCoach)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateState(
        id: String? = _state.value.idWorker,
        name: String? = _state.value.nameWorker,
        type: String? = _state.value.typeWorker,
        violations: Map<Int, ViolationDomain>? = _state.value.violations,
        idError: String? = _state.value.idError,
        nameError: String? = _state.value.nameError,
        typeError: String? = _state.value.typeError
    ) {
        val isEnableToSave = idError == null &&
                nameError == null &&
                typeError == null
        _state.value = DinnerCoachState(
            idWorker = id,
            nameWorker = name,
            typeWorker = type,
            violations = violations,
            idError = idError,
            nameError = nameError,
            typeError = typeError,
            isSaveEnabled = isEnableToSave
        )
    }

    private fun deletePhotoViolation(code: Int, coachNumber: String, orderNumber: String) {
        viewModelScope.launch {
            deleteViolationPhotoUseCase.invoke(code, orderNumber, coachNumber)
        }
    }
}