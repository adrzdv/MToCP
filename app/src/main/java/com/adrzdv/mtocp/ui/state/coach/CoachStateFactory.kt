package com.adrzdv.mtocp.ui.state.coach

import com.adrzdv.mtocp.domain.model.enums.CoachTypes

object CoachStateFactory {
    fun create(type: CoachTypes): NewCoachState {
        return when(type) {
            CoachTypes.PASSENGER_CAR -> NewPassengerCoachState(CoachTypes.PASSENGER_CAR)
            CoachTypes.DINNER_CAR -> NewDinnerCoachState(CoachTypes.DINNER_CAR)
            CoachTypes.COMMERCIAL_CAR -> TODO()
        }
    }
}