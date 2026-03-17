package com.adrzdv.mtocp.ui.state.coach

import com.adrzdv.mtocp.service.stringprovider.StringProvider

/**
 * Represents the UI state for the screen where a new coach is created or an existing one is edited.
 *
 * This sealed interface defines the contract for handling input validation and determining
 * the availability of the save action based on the current state of the coach data.
 */
sealed interface NewCoachState {
    fun validate(stringProvider: StringProvider): NewCoachState
    fun isSaveEnabled(): Boolean
}