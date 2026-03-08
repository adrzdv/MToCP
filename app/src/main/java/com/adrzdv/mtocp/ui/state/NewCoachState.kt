package com.adrzdv.mtocp.ui.state

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
    val numberError: String? = null,
    val selectedTypeError: String? = null,
    val selectedDepotError: String? = null,
    val routeError: String? = null,

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

        return copy(
            numberError = numberError,
            selectedTypeError = typeError,
            selectedDepotError = depotError,
            routeError = routeError,
            isValidationStarted = true
        )
    }

    val isSaveEnabled: Boolean
        get() = numberError == null &&
                selectedTypeError == null &&
                selectedDepotError == null &&
                routeError == null

}
