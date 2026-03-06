package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.lifecycle.viewModelScope
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.domain.model.order.TrainOrder
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject
import com.adrzdv.mtocp.domain.usecase.GetDepotByNameUseCase
import com.adrzdv.mtocp.mapper.WorkerMapper
import com.adrzdv.mtocp.ui.model.statedtoui.TrainUI
import com.adrzdv.mtocp.ui.model.statedtoui.WorkerUI
import com.adrzdv.mtocp.ui.state.order.PickerField
import com.adrzdv.mtocp.ui.state.order.TrainOrderState
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TrainOrderViewModel(
    appDependencies: AppDependencies,
    val getDepotByNameUseCase: GetDepotByNameUseCase
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

    override fun onAddPersonCrew(worker: WorkerUI) {
        viewModelScope.launch {
            val depot = getDepotByNameUseCase(worker.depot, false)
            val workerDomain = WorkerMapper.fromUiToDomain(worker, depot)
            updateState { current ->
                val updatedCrewMap = current.crewList.toMutableMap()
                updatedCrewMap[worker.id] = worker
                current.copy(
                    crewList = updatedCrewMap
                )
            }
        }
    }

    override fun onDeletePersonCrew(worker: WorkerUI) {
        val updatedCrewMap = orderState.value.crewList.toMutableMap()
        updatedCrewMap.remove(worker.id)
        updateState { current ->
            current.copy(
                crewList = updatedCrewMap
            )
        }
    }

    override fun onClearCrew() {
        updateState { current ->
            current.copy(
                crewList = emptyMap()
            )
        }
    }

    override fun onSave() {

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

    fun onOrderRouteChange(route: String) {
        updateState { current ->
            current.copy(
                route = route,
                routeError = when {
                    route.isEmpty() -> appDependencies.stringProvider
                        .getString(R.string.route_error)

                    else -> null
                }
            )
        }
    }

    fun onTrainSelected(str: String) {
        updateState { current ->
            current.copy(
                train = TrainUI(
                    number = str.substringBefore(" "),
                    route = str.substringAfter(" ")
                )
            )
        }
    }

    fun onQualityPassportChange(checked: Boolean) {
        updateState { current ->
            current.copy(
                isQualityPassport = checked
            )
        }
    }

    fun onShowDialog() {
        updateState { current ->
            current.copy(
                showDialogs = true
            )
        }
    }

    fun onDismissDialog() {
        updateState { current ->
            current.copy(
                showDialogs = false
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


    override fun updatePersonInCrew() {
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