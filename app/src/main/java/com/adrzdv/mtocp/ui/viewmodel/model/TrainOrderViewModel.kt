package com.adrzdv.mtocp.ui.viewmodel.model

import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.domain.model.order.TrainOrder
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain
import com.adrzdv.mtocp.ui.state.order.PickerField
import com.adrzdv.mtocp.ui.state.order.TrainOrderState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TrainOrderViewModel(
    appDependencies: AppDependencies
) : BaseOrderViewModel<TrainOrderState, TrainOrder>(appDependencies) {

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
                    number.isEmpty() -> appDependencies.stringProvider
                        .getString(R.string.empty_string)

                    !current.isOrderNumberValid -> appDependencies
                        .stringProvider
                        .getString(R.string.number_pattern_error)

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

    fun onPickDate(field: PickerField) {
        updateState { current ->
            current.copy(
                showDatePicker = true,
                pickerVisibleFor = field
            )
        }
    }

    fun onHidePickDate() {
        updateState { current ->
            current.copy(
                showDatePicker = false,
                pickerVisibleFor = null
            )
        }
    }

    fun onDateSelected(year: Int, month: Int, day: Int) {
        val selectedDate = orderState.value.dateStart
            .withYear(year)
            .withMonth(month)
            .withDayOfMonth(day)

        updateState { current ->
            when (current.pickerVisibleFor) {
                PickerField.START_DATE -> current.copy(
                    dateStart = selectedDate,
                    dateStartError = when {
                        !validateDate(selectedDate) -> appDependencies
                            .stringProvider
                            .getString(R.string.date_error)

                        else -> null
                    },
                    showDatePicker = false,
                    showTimePicker = true
                )

                PickerField.END_DATE -> current.copy(
                    dateEnd = selectedDate,
                    dateEndError = when {
                        !validateDate(selectedDate) -> appDependencies
                            .stringProvider.getString(R.string.date_error)

                        else -> null
                    },
                    showDatePicker = false,
                    showTimePicker = true
                )

                else -> current
            }
        }
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        val gottenDate = when (orderState.value.pickerVisibleFor) {
            PickerField.START_DATE -> orderState.value.dateStart
            PickerField.END_DATE -> orderState.value.dateEnd
            else -> LocalDateTime.now()
        }
        val currentDate = gottenDate
            .withHour(hour)
            .withMinute(minute)

        updateState { current ->
            when (current.pickerVisibleFor) {
                PickerField.START_DATE -> current.copy(
                    dateStart = currentDate,
                    dateStartError = when {
                        !validateDate(currentDate) -> appDependencies
                            .stringProvider
                            .getString(R.string.date_error)

                        else -> null
                    },
                    showTimePicker = false,
                    pickerVisibleFor = null
                )

                PickerField.END_DATE -> current.copy(
                    dateEnd = currentDate,
                    dateEndError = when {
                        !validateDate(currentDate) -> appDependencies
                            .stringProvider
                            .getString(R.string.date_error)

                        else -> null
                    },
                    showTimePicker = false,
                    pickerVisibleFor = null
                )

                else -> current
            }

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

    private fun validateDate(date: LocalDateTime): Boolean {
        return when (orderState.value.pickerVisibleFor) {
            PickerField.START_DATE -> date.isAfter(LocalDateTime.now().minusHours(1L))
            PickerField.END_DATE -> date.isAfter(orderState.value.dateStart)
            else -> false
        }
    }

}