package com.adrzdv.mtocp.ui.viewmodel.model

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.lifecycle.ViewModel
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.domain.model.order.Order
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain
import com.adrzdv.mtocp.ui.state.order.OrderDraftState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar

/**
 * Base [ViewModel] for managing order-related logic and UI state within the application.
 *
 * This abstract class serves as the foundation for specific order implementations, providing
 * a bridge between domain models ([Order]) and UI states ([OrderDraftState]). It encapsulates
 * common operations for manipulating order components such as revision objects and crew members.
 *
 * @param S The specific type of the UI state, which must extend [OrderDraftState].
 * @param O The specific type of the domain model, which must extend [Order].
 * @property appDependencies Global dependencies providing access to repositories and business logic.
 */
abstract class BaseOrderViewModel<S : OrderDraftState, O : Order>(
    protected val appDependencies: AppDependencies
) : ViewModel() {
    protected val domainOrder: O by lazy { createOrder() }
    private val _orderState: MutableStateFlow<S> by lazy {
        MutableStateFlow(createInitialState())
    }
    val orderState: StateFlow<S> = _orderState
    protected fun updateState(transform: (S) -> S) {
        _orderState.update(transform)
    }

    abstract fun createOrder(): O
    abstract fun createInitialState(): S
    abstract fun onNumberChange(number: String)

    protected abstract fun updateOrderNumber(number: String, error: String)
    protected abstract fun addRevisionObjectInOrder(o: RevisionObject)
    protected abstract fun removeRevisionObjectFromOrder(o: RevisionObject)
    protected abstract fun updateRevisionObjectInOrder(o: RevisionObject)
    protected abstract fun clearRevisionObjects()
    protected abstract fun addPersonInCrew(person: WorkerDomain)
    protected abstract fun updatePersonInCrew()
    protected abstract fun clearCrew()
    protected abstract fun getTotalViolationsSum(): Int
}