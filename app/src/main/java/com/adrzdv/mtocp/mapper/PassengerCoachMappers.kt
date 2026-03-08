package com.adrzdv.mtocp.mapper

import com.adrzdv.mtocp.domain.model.departments.DepotDomain
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.ui.model.statedtoui.CoachUi
import com.adrzdv.mtocp.ui.state.NewCoachState
import java.time.LocalDateTime

fun CoachUi.toDomain(depot: DepotDomain) = PassengerCar(number).apply {
    this.depotDomain = depot
    this.coachType = PassengerCoachType.fromString(type)
    this.coachRoute = route
    this.revisionDateStart = revisionStart ?: LocalDateTime.now()
    this.revisionDateEnd = revisionEnd ?: LocalDateTime.now()
}

fun NewCoachState.toDomain(depot: DepotDomain) = PassengerCar(number).apply {
    this.depotDomain = depot
    this.coachType = PassengerCoachType.fromString(selectedType)
    this.coachRoute = route
}

fun NewCoachState.toUI() = CoachUi().copy(
    number = number,
    route = route,
    type = selectedType,
    depot = selectedDepot ?: "",
    isTrailing = isTrailing
)