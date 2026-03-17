package com.adrzdv.mtocp.ui.state.coach

import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import com.adrzdv.mtocp.service.stringprovider.StringProvider
import com.adrzdv.mtocp.ui.validation.Validator

data class NewDinnerCoachState(
    val globalCoachType: CoachTypes,
    val number: String = "",
    val selectedType: String = "",
    val selectedDepot: String? = null,
    val selectedCompany: String? = null,
    val workerId: String? = null,
    val workerName: String = "",
    val workerPosition: String = "",
    val isRentalCoach: Boolean = false,

    val numberError: String? = null,
    val selectedTypeError: String? = null,
    val selectedDepotError: String? = null,
    val selectedCompanyError: String? = null,

    val workerIdError: String? = null,
    val workerNameError: String? = null,
    val workerPositionError: String? = null,

    val isValidationStarted: Boolean = false

) : NewCoachState {

    private val basicErrors = listOf(
        numberError,
        selectedTypeError,
        workerIdError,
        workerNameError,
        workerPositionError
    )

    private val workerEmployerError = listOf(
        selectedDepotError,
        selectedCompanyError
    )

    override fun validate(stringProvider: StringProvider): NewCoachState {
        val numberError = when {
            number.isBlank() -> stringProvider.getString(R.string.empty_string)
            !Validator.validateCoachNumber(number) -> stringProvider.getString(R.string.coach_number_invalid)
            else -> null
        }

        val typeError = when {
            selectedType.isBlank() -> stringProvider.getString(R.string.empty_string)
            !Validator.validateDinnerCar(number) -> stringProvider.getString(
                R.string.coach_type_invalid
            )

            else -> null
        }

        val depotError =
            if (selectedCompany == null && selectedDepot.isNullOrBlank())
                stringProvider.getString(R.string.empty_string)
            else null

        val companyError =
            if (selectedDepot.isNullOrEmpty() && selectedDepot == null)
                stringProvider.getString(R.string.empty_string)
            else null

        val workerIdError =
            when {
                workerId?.isEmpty() == true -> stringProvider.getString(R.string.empty_string)
                workerId?.toIntOrNull() == null -> stringProvider.getString(R.string.invalid_worker_id)
                else -> null
            }

        val workerNameError =
            when {
                workerName.isBlank() -> stringProvider.getString(R.string.empty_string)
                !Validator.validateWorkerName(workerName) -> stringProvider.getString(R.string.invalid_input_type)
                else -> null
            }

        val workerPositionError =
            when {
                workerPosition.isEmpty() -> stringProvider.getString(R.string.empty_string)
                else -> null
            }

        return copy(
            numberError = numberError,
            selectedTypeError = typeError,
            selectedDepotError = depotError,
            selectedCompanyError = companyError,
            workerIdError = workerIdError,
            workerNameError = workerNameError,
            workerPositionError = workerPositionError
        )
    }

    override fun isSaveEnabled(): Boolean =
        basicErrors.all { it == null } && workerEmployerError.any { it == null }


}