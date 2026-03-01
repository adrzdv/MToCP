package com.adrzdv.mtocp.ui.state.order

import java.time.LocalDateTime

interface OrderDraftState {
    val orderNumber: String
    val dateStart: LocalDateTime
    val dateEnd: LocalDateTime
    val route: String

    val isOrderNumberValid: Boolean
        get() {
            val regex = Regex("""\d{4}/С-ЗАП/\d{4}""")
            return regex.matches(orderNumber) && orderNumber.isNotBlank()
        }

    val isDateStartValid: Boolean
        get() = dateStart.isBefore(dateEnd)
                && dateStart.isAfter(LocalDateTime.now())

    val isDateEndValid: Boolean
        get() = dateStart.isAfter(dateStart)

    val isRouteValid: Boolean
        get() = route.isNotBlank()
}