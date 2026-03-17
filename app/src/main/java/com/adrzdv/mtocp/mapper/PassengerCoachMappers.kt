package com.adrzdv.mtocp.mapper

import com.adrzdv.mtocp.domain.model.departments.CompanyDomain
import com.adrzdv.mtocp.domain.model.departments.DepotDomain
import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import com.adrzdv.mtocp.domain.model.enums.DinnerCarsType
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.ui.model.statedtoui.CoachUi
import com.adrzdv.mtocp.ui.model.statedtoui.DinnerCarUI
import com.adrzdv.mtocp.ui.state.coach.NewDinnerCoachState
import com.adrzdv.mtocp.ui.state.coach.NewPassengerCoachState
import java.time.LocalDateTime

fun CoachUi.toDomain(depot: DepotDomain) = PassengerCar(number, this.globalType).apply {
    this.depotDomain = depot
    this.coachType = PassengerCoachType.fromString(type)
    this.coachRoute = route
    this.revisionDateStart = revisionStart ?: LocalDateTime.now()
    this.revisionDateEnd = revisionEnd ?: LocalDateTime.now()
}

fun NewPassengerCoachState.toDomain(depot: DepotDomain) =
    PassengerCar(number, CoachTypes.PASSENGER_CAR).apply {
        this.depotDomain = depot
        this.coachType = PassengerCoachType.fromString(selectedType)
        this.coachRoute = route
    }

fun NewPassengerCoachState.toUI() = CoachUi().copy(
    globalType = globalCoachType,
    number = number,
    route = route,
    type = selectedType,
    depot = selectedDepot ?: "",
    isTrailing = isTrailing,
    workerId = workerId,
    workerName = workerName,
    workerPosition = workerPosition,
    workerDepot = workerDepot
)

fun NewDinnerCoachState.toUI() = DinnerCarUI().copy(
    number = number,
    type = selectedType,
    depot = selectedDepot ?: "",
    company = selectedCompany ?: "",
    workerId = workerId,
    workerName = workerName,
    workerPosition = workerPosition
)

fun DinnerCarUI.toDomain(depot: DepotDomain? = null, company: CompanyDomain? = null) =
    DinnerCar(number, CoachTypes.DINNER_CAR).apply {
        this.depot?.let {
            this.depot = depot
        }

        this.companyDomain?.let {
            this.companyDomain = company
        }

        this.dinnerType = DinnerCarsType.fromString(type)
        this.revisionDateStart = revisionStart ?: LocalDateTime.now()
        this.revisionDateEnd = revisionEnd ?: LocalDateTime.now()
    }

//fun ViolationUi.toDomain(): ViolationDomain {
//
//}