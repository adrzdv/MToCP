package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.lifecycle.ViewModel
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import com.adrzdv.mtocp.mapper.toDraftState
import com.adrzdv.mtocp.ui.model.dto.CoachUIBase
import com.adrzdv.mtocp.ui.model.dto.WorkerUI
import com.adrzdv.mtocp.ui.state.coach.CoachDraftState
import com.adrzdv.mtocp.ui.validation.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class EditCoachViewModel(
    coach: CoachUIBase? = null,
    coachType: String? = null,
    val appDependencies: AppDependencies,
    private val appCacheRepository: CacheRepository,
) : ViewModel() {

    private val _state =
        MutableStateFlow(
            coach?.toDraftState() ?: CoachDraftState(
                id = UUID.randomUUID(),
                CoachTypes.fromString(coachType!!)
            )
        )
    val state: StateFlow<CoachDraftState> = _state.asStateFlow()

    private val _isSheetCoachEditOpen = MutableStateFlow(false)
    val isSheetCoachEditOpen: StateFlow<Boolean> = _isSheetCoachEditOpen.asStateFlow()

    private val _isSheetWorkerEditOpen = MutableStateFlow(false)
    val isSheetWorkerEditOpen: StateFlow<Boolean> = _isSheetWorkerEditOpen.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    fun openCoachSheet() = run { _isSheetCoachEditOpen.value = true }
    fun openWorkerSheet() = run { _isSheetWorkerEditOpen.value = true }
    fun closeAnySheet() = run {
        _isSheetCoachEditOpen.value = false
        _isSheetWorkerEditOpen.value = false
    }

    fun onCoachDataChange(draftState: CoachDraftState) {
        _state.update { currState ->
            currState.copy(
                number = draftState.number,
                type = draftState.type,
                depot = draftState.depot
            )
        }
    }

    fun onNumberChange(newNumber: String) {
        _state.update { currState ->
            currState.copy(
                number = newNumber,
                numberError = if (Validator.validateCoachNumber(newNumber)) {
                    null
                } else {
                    when {
                        newNumber.isEmpty() -> appDependencies.stringProvider.getString(R.string.empty_string)
                        else -> appDependencies.stringProvider.getString(R.string.coach_number_invalid)
                    }
                }
            )
        }
    }

    fun onTypeSelected(selectedType: String) {
        _state.update { currState ->
            currState.copy(
                type = selectedType,
                typeError = null
            )

        }
    }

    fun onDepotSelected(str: String) {
        _state.update { curr ->
            curr.copy(
                depot = str,
                depotError = if (str.isEmpty()) {
                    appDependencies.stringProvider.getString(R.string.empty_string)
                } else null
            )
        }
    }

    fun onCompanySelected(str: String) {
        _state.update { curr ->
            curr.copy(
                company = str,
                companyError = if (str.isEmpty()) {
                    appDependencies.stringProvider.getString(R.string.empty_string)
                } else null
            )
        }
    }

    fun onTrailingSelected() {
        _state.update { curr ->
            curr.copy(
                isTrailing = !(curr.isTrailing ?: false)
            )
        }
    }

    fun onWorkerDataChange(worker: WorkerUI) {
        _state.update { curr ->
            curr.copy(
                worker = worker,
                workerError = when {
                    worker.id.isEmpty() -> appDependencies.stringProvider.getString(R.string.invalid_worker_id)
                    worker.id.toIntOrNull() == null -> appDependencies.stringProvider.getString(R.string.invalid_worker_id)
                    !Validator.validateWorkerName(worker.name) -> appDependencies.stringProvider.getString(
                        R.string.invalid_worker_name
                    )

                    worker.name.isEmpty() -> appDependencies.stringProvider.getString(R.string.invalid_worker_name)
                    worker.depot.isEmpty() -> appDependencies.stringProvider.getString(R.string.empty_depot)
                    worker.position.isEmpty() -> appDependencies.stringProvider.getString(R.string.empty_position)
                    else -> null
                }
            )
        }
    }

    fun createWorkerUi(
        id: String,
        name: String,
        position: String,
        depot: String
    ): WorkerUI =
        WorkerUI(
            id,
            name,
            position,
            depot
        )

    fun onRouteChange() {

    }


    fun onWorkerAdd() {

    }

    fun onStatParamsAdd() {

    }

    fun onViolationAdd() {

    }

    fun onViolationDelete() {

    }

    fun onViolationCleanup() {

    }

    fun onChangeViolationAmount() {

    }

    fun onViolationResolved() {

    }

    fun onPhotoAdd() {

    }

    fun onAddTagToViolation() {

    }

}