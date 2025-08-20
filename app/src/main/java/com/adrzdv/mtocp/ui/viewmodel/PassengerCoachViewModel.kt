package com.adrzdv.mtocp.ui.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain
import com.adrzdv.mtocp.ui.state.PassengerCoachState
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.domain.model.violation.StaticsParam
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain
import com.adrzdv.mtocp.domain.usecase.DeleteViolationPhotoUseCase
import com.adrzdv.mtocp.domain.usecase.GetDepotByNameUseCase
import com.adrzdv.mtocp.ui.activities.CameraActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class PassengerCoachViewModel(
    private val initCoach: PassengerCar,
    private val onSaveCallback: (PassengerCar) -> Unit,
    private val getDepotByNameUseCase: GetDepotByNameUseCase,
    private val deleteViolationPhotoUseCase: DeleteViolationPhotoUseCase
) : ViewModel() {
    private val _state = mutableStateOf(
        PassengerCoachState(
            idWorker = initCoach.worker?.id?.toString() ?: "",
            nameWorker = initCoach.worker?.name,
            typeWorker = initCoach.worker?.workerType?.description,
            depotWorker = (initCoach.worker as? InnerWorkerDomain)?.depotDomain?.name,
            violations = initCoach.violationMap,
            statParams = initCoach.additionalParams
        )
    )
    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()
    val state: State<PassengerCoachState> = _state

    fun getDisplayViolations(): List<ViolationDomain> {
        return _state.value.violations?.values?.toList() ?: mutableListOf()
    }

    fun cleanViolations(orderNumber: String) {
        val updatedMap = _state.value.violations?.toMutableMap()
        for (violation in updatedMap?.values!!) {
            deletePhotoViolation(violation.code, initCoach.number, orderNumber)
        }
        updatedMap.clear()
        updateState(violations = updatedMap)
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

    fun onDepotChange(newDepot: String, errorMessage: String) {
        _state.value = _state.value.copy(
            depotWorker = newDepot,
            depotError = when {
                newDepot.isEmpty() -> errorMessage
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
        initCoach.additionalParams = statparams
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

    fun deleteViolation(code: Int, orderNumber: String) {
        val updatedMap = _state.value.violations?.toMutableMap()
        deletePhotoViolation(code, initCoach.number, orderNumber)
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
            depotWorker = ""
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
        val depotValid = !current.depotWorker.isNullOrBlank()
        val paramsValid = current.statParams != null && current.statParams!!.isNotEmpty()

        if (!idValid ||
            !nameValid ||
            !typeValid ||
            !depotValid
        ) {
            updateState(
                idError = if (!idValid) MessageCodes.EMPTY_STRING.errorTitle else null,                 //строки бы нужно было получить как string resource!
                nameError = if (!nameValid) MessageCodes.EMPTY_STRING.errorTitle else null,
                typeError = if (!typeValid) MessageCodes.EMPTY_STRING.errorTitle else null,
                depotError = if (!depotValid) MessageCodes.EMPTY_STRING.errorTitle else null
            )
            return
        }
        if (!paramsValid) {
            _snackbarMessage.value = MessageCodes.PARAMS_NOT_DONE.errorTitle
            return
        }

        viewModelScope.launch {
            current.idWorker?.let { id ->
                current.nameWorker?.let { name ->
                    current.typeWorker?.let { type ->
                        current.violations?.let { currViolations ->
                            current.statParams?.let { currParams ->
                                val workerType = WorkerTypes.entries.find { it.description == type }
                                current.depotWorker?.let { depot ->
                                    val depotDomain = getDepotByNameUseCase(depot, false)
                                    val worker = InnerWorkerDomain(
                                        id.toInt(),
                                        name,
                                        depotDomain,
                                        workerType
                                    )
                                    val updatedCoach = PassengerCar(initCoach.number).apply {
                                        this.coachRoute = initCoach.coachRoute
                                        this.depotDomain = initCoach.depotDomain
                                        this.coachType = initCoach.coachType
                                        this.trailing = initCoach.trailing
                                        this.revisionDateStart = initCoach.revisionDateStart
                                        if (initCoach.revisionDateEnd == null) {
                                            this.revisionDateEnd = LocalDateTime.now()
                                        } else {
                                            this.revisionDateEnd = initCoach.revisionDateEnd
                                        }
                                        this.additionalParams = currParams
                                        this.depotDomain = initCoach.depotDomain
                                        this.worker = worker
                                        this.qualityPassport = initCoach.qualityPassport
                                        this.violationMap = currViolations
                                    }
                                    onSaveCallback(updatedCoach)
                                }
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
        depot: String? = _state.value.depotWorker,
        violations: Map<Int, ViolationDomain>? = _state.value.violations,
        idError: String? = _state.value.idError,
        nameError: String? = _state.value.nameError,
        typeError: String? = _state.value.typeError,
        depotError: String? = _state.value.depotError
    ) {
        val isEnableToSave = idError == null &&
                nameError == null &&
                typeError == null &&
                depotError == null
        _state.value = PassengerCoachState(
            idWorker = id,
            nameWorker = name,
            typeWorker = type,
            depotWorker = depot,
            violations = violations,
            idError = idError,
            nameError = nameError,
            typeError = typeError,
            depotError = depotError,
            isSaveEnabled = isEnableToSave
        )
    }

    private fun deletePhotoViolation(code: Int, coachNumber: String, orderNumber: String) {
        viewModelScope.launch {
            deleteViolationPhotoUseCase.invoke(code, orderNumber, coachNumber)
        }
    }
}