package com.adrzdv.mtocp.ui.state.coach

import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.service.stringprovider.StringProvider
import com.adrzdv.mtocp.ui.validation.Validator

data class NewCoachState(
    val number: String = "",
    val selectedType: String = "",
    val isCopyDepot: Boolean = true,
    val isTrailing: Boolean = false,
    val selectedDepot: String? = null,
    val route: String? = null,
    val workerId: String? = null,
    val workerName: String? = null,
    val workerPosition: String? = null,
    val workerDepot: String? = null,
    val isWorkerAddSelected: Boolean = false,
    val numberError: String? = null,
    val selectedTypeError: String? = null,
    val selectedDepotError: String? = null,
    val routeError: String? = null,
    val workerIdError: String? = null,
    val workerNameError: String? = null,
    val workerDepotError: String? = null,
    val workerPositionError: String? = null,

    val isValidationStarted: Boolean = false
) {

    fun validate(stringProvider: StringProvider): NewCoachState {

        val numberError = when {
            number.isBlank() -> stringProvider.getString(R.string.empty_string)
            !Validator.validateCoachNumber(number) -> stringProvider.getString(R.string.coach_number_invalid)
            else -> null
        }

        val typeError = when {
            selectedType.isBlank() -> stringProvider.getString(R.string.empty_string)
            !Validator.validatePassengerCoachType(number, selectedType) -> stringProvider.getString(
                R.string.coach_type_invalid
            )

            else -> null
        }

        val depotError =
            if (!isCopyDepot && selectedDepot.isNullOrBlank())
                stringProvider.getString(R.string.empty_string)
            else null

        val routeError =
            if (isTrailing && route.isNullOrBlank())
                stringProvider.getString(R.string.empty_string)
            else null

        val workerNameError = if (isWorkerAddSelected) {
            when {
                workerName.isNullOrBlank() -> stringProvider.getString(R.string.empty_string)
                !Validator.validateWorkerName(workerName) -> stringProvider.getString(R.string.invalid_input_type)
                else -> null
            }
        } else null

        val workerIdError = if (isWorkerAddSelected) {
            when {
                workerId?.isEmpty() == true -> stringProvider.getString(R.string.empty_string)
                workerId?.toIntOrNull() == null -> stringProvider.getString(R.string.invalid_worker_id)
                else -> null
            }
        } else null

        val workerDepotError = if (isWorkerAddSelected) {
            when {
                workerDepot.isNullOrEmpty() -> stringProvider.getString(R.string.empty_string)
                else -> null
            }
        } else null

        val workerPositionError = if (isWorkerAddSelected) {
            when {
                workerPosition.isNullOrEmpty() -> stringProvider.getString(R.string.empty_string)
                else -> null
            }
        } else null

        return copy(
            numberError = numberError,
            selectedTypeError = typeError,
            selectedDepotError = depotError,
            routeError = routeError,
            workerNameError = workerNameError,
            workerIdError = workerIdError,
            workerDepotError = workerDepotError,
            workerPositionError = workerPositionError,
            isValidationStarted = true
        )
    }

    val isSaveEnabled: Boolean
        get() = numberError == null &&
                selectedTypeError == null &&
                selectedDepotError == null &&
                routeError == null &&
                workerNameError == null &&
                workerIdError == null &&
                workerPositionError == null &&
                workerDepotError == null

}