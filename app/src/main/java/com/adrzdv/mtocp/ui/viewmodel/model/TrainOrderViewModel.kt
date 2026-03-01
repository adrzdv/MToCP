package com.adrzdv.mtocp.ui.viewmodel.model

import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.domain.model.order.TrainOrder
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain
import com.adrzdv.mtocp.ui.state.order.TrainOrderState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TrainOrderViewModel(
    appDependencies: AppDependencies
) : BaseOrderViewModel<TrainOrderState, TrainOrder>(appDependencies) {
    val pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

    override fun createOrder(): TrainOrder {
        return TrainOrder(
            orderState.value.orderNumber,
            orderState.value.dateStart,
            orderState.value.dateEnd,
            orderState.value.route
        )
    }

    override fun createInitialState(): TrainOrderState {
        return TrainOrderState(
            orderConditions = null,
            crewList = emptyMap(),
            coachList = emptyMap(),
        )
    }

    override fun updateOrderNumber(number: String, error: String) {
        updateState { current ->
            current.copy(
                orderNumber = number,
                numberError = when {
                    number.isEmpty() -> error
                    else -> null
                }
            )
        }
    }

    override fun onNumberChange(number: String) {
        updateState { current ->
            current.copy(
                orderNumber = number,
                numberError = when {
                    number.isEmpty() -> R.string.empty_string.toString()
                    !current.isOrderNumberValid -> R.string.number_pattern_error.toString()
                    else -> null
                }
            )
        }
    }

    fun onConditionsChange(condition: String) {
        updateState { current ->
            current.copy(
                orderConditions = RevisionType.fromString(condition)
            )
        }
    }

    fun onPickDate() {
        updateState { current ->
            current.copy(
                showDatePicker = true
            )
        }
    }

    fun onHidePickDate() {
        updateState { current ->
            current.copy(
                showDatePicker = false
            )
        }
    }

    fun onDateStartSelected(year: Int, month: Int, day: Int) {
        val dateStart = orderState.value.dateStart
            .withYear(year)
            .withMonth(month)
            .withDayOfMonth(day)

        updateState { current ->
            current.copy(
                dateStart = dateStart,
                showDatePicker = false,
                showTimePicker = true
            )
        }
    }

    fun onTimeStartSelected(hour: Int, minute: Int) {
        val dateStart = orderState.value.dateStart
            .withHour(hour)
            .withMinute(minute)

        updateState { current ->
            current.copy(
                dateStart = dateStart,
                showTimePicker = false,
            )
        }
    }

    fun onDateStartChange(date: String) {
        updateState { current ->
            current.copy(
                dateStart = LocalDateTime.parse(date, pattern),
                dateStartError = when {
                    !current.isDateStartValid -> R.string.date_error.toString()
                    else -> null
                }
            )
        }
    }

    fun onDateEndChange(date: String) {
        updateState { current ->
            current.copy(
                dateEnd = LocalDateTime.parse(date, pattern),
                dateEndError = when {
                    !current.isDateEndValid -> R.string.date_error.toString()
                    else -> null
                }
            )

        }
    }

    private fun setOrderConditions(conditions: RevisionType) {
        updateState { current ->
            current.copy(
                orderConditions = conditions
            )
        }
    }

    override fun addRevisionObjectInOrder(o: RevisionObject) {
        TODO("Not yet implemented")
    }

    override fun removeRevisionObjectFromOrder(o: RevisionObject) {
        TODO("Not yet implemented")
    }

    override fun updateRevisionObjectInOrder(o: RevisionObject) {
        TODO("Not yet implemented")
    }

    override fun clearRevisionObjects() {
        TODO("Not yet implemented")
    }

    override fun addPersonInCrew(person: WorkerDomain) {
        TODO("Not yet implemented")
    }

    override fun updatePersonInCrew() {
        TODO("Not yet implemented")
    }

    override fun clearCrew() {
        TODO("Not yet implemented")
    }

    override fun getTotalViolationsSum(): Int {
        TODO("Not yet implemented")
    }

}