package com.adrzdv.mtocp.ui.validation

import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType

object Validator {
    private val _WORKER_NAME_REGEX =
        Regex("""^[А-ЯЁ][а-яё]+(?:[-][А-ЯЁ][а-яё]+)?(?: (?:[А-ЯЁ][а-яё]+|[А-ЯЁ]\.|[А-ЯЁ]\.[А-ЯЁ]\.?)){0,2}$""")
    private val _REGULAR_COACH = Regex("""^\d{3}-\d{5}$""")
    private val _SUBURB_COACH = Regex("""^\d{5}$""")

    fun validateWorkerName(name: String): Boolean {
        return _WORKER_NAME_REGEX.matches(name.trim())
    }

    fun validateCoachNumber(number: String): Boolean {
        return _REGULAR_COACH.matches(number) || _SUBURB_COACH.matches(number)
    }

    fun validatePassengerCoachType(number: String, selectedType: String): Boolean {

        val type = PassengerCoachType.fromString(selectedType)

        val digit = number
            .substringAfter("-", "")
            .firstOrNull()
            ?.digitToIntOrNull()

        return when (type) {
            PassengerCoachType.INTERREGIONAL ->
                number.length == 5 || digit == 3

            PassengerCoachType.LUXURY,
            PassengerCoachType.FIRST_CLASS_SLEEPER ->
                digit == 0

            PassengerCoachType.COMPARTMENT ->
                digit == 0 || digit == 1

            PassengerCoachType.OPEN_CLASS_SLEEPING ->
                digit == 2
        }
    }

    fun validateDinnerCar(number: String): Boolean {
        val digit = number
            .substringAfter("-", "")
            .firstOrNull()
            ?.digitToIntOrNull()

        return when (digit) {
            6 -> true
            else -> false
        }
    }
}