package com.adrzdv.mtocp.ui.viewmodel.model

import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.domain.usecase.CreatePassengerCoachUseCase
import com.adrzdv.mtocp.domain.usecase.GetDepotByNameUseCase
import com.adrzdv.mtocp.domain.usecase.GetTrainByNumberUseCase
import com.adrzdv.mtocp.domain.usecase.GetTrainSchemeUseCase
import com.adrzdv.mtocp.ui.model.dto.WorkerUI
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TrainOrderViewModelTest {

    private val appDependencies: AppDependencies = mockk(relaxed = true)
    private val getDepotUseCase: GetDepotByNameUseCase = mockk()
    private val getTrainUseCase: GetTrainByNumberUseCase = mockk()
    private val createCoachUseCase: CreatePassengerCoachUseCase = mockk()
    private val getTrainSchemeUseCase: GetTrainSchemeUseCase = mockk()

    private lateinit var viewModel: TrainOrderViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = TrainOrderViewModel(
            appDependencies,
            getDepotUseCase,
            getTrainUseCase,
            createCoachUseCase,
            getTrainSchemeUseCase
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onAddPersonCrew_validWorker_updatesStateWithNewWorker`() = runTest {
        // GIVEN
        val worker =
            WorkerUI(id = 1, name = "John Doe", position = "Conductor", depot = "Main Depot")
        coEvery { getDepotUseCase(any(), any()) } returns mockk(relaxed = true)

        // WHEN
        viewModel.onAddPersonCrew(worker)
        advanceUntilIdle()

        // THEN
        val currentState = viewModel.orderState.value
        assertTrue(currentState.crewList.containsKey(1))
        assertEquals("John Doe", currentState.crewList[1]?.name)
    }

    @Test
    fun `onSave_incompleteOrder_returnsFalseAndSetsErrors`() = runTest {
        // GIVEN
        // Initial state is incomplete (empty number, route, etc.)
        every { appDependencies.stringProvider.getString(any()) } returns "Error"

        // WHEN
        val result = viewModel.onSave()

        // THEN
        assertFalse(result)
        val state = viewModel.orderState.value
        assertNotNull(state.numberError)
        assertNotNull(state.routeError)
        assertNotNull(state.emptyCrewError)
    }

    @Test
    fun `onNumberChange_validInput_updatesOrderNumberInState`() {
        // GIVEN
        val newNumber = "TR-123"

        // WHEN
        viewModel.onNumberChange(newNumber)

        // THEN
        assertEquals(newNumber, viewModel.orderState.value.orderNumber)
    }
}
