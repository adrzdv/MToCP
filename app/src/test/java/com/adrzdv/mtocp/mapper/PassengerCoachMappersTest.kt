package com.adrzdv.mtocp.mapper

import com.adrzdv.mtocp.domain.model.departments.DepotDomain
import com.adrzdv.mtocp.ui.state.coach.NewCoachState
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PassengerCoachMappersTest {

    @Test
    fun `toDomain_validNewCoachState_mapsFieldsCorrectly`() {
        // GIVEN
        val state = NewCoachState(
            number = "04812345",
            route = "Kiev-Lviv",
            selectedType = "Platskart"
        )
        val depot: DepotDomain = mockk()
        every { depot.shortName } returns "VCH-1"

        // WHEN
        val domain = state.toDomain(depot)

        // THEN
        assertEquals("04812345", domain.number)
        assertEquals("Kiev-Lviv", domain.coachRoute)
        assertEquals(depot, domain.depotDomain)
    }

    @Test
    fun `toUI_validNewCoachState_mapsToCoachUiCorrectly`() {
        // GIVEN
        val state = NewCoachState(
            number = "04812345",
            route = "Kiev-Lviv",
            selectedType = "Platskart",
            selectedDepot = "VCH-1",
            isTrailing = true
        )

        // WHEN
        val ui = state.toUI()

        // THEN
        assertEquals("04812345", ui.number)
        assertEquals("Kiev-Lviv", ui.route)
        assertEquals("Platskart", ui.type)
        assertEquals("VCH-1", ui.depot)
        assertEquals(true, ui.isTrailing)
    }
}
