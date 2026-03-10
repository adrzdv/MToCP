package com.adrzdv.mtocp.domain.usecase

import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain
import com.adrzdv.mtocp.mapper.toDomain
import com.adrzdv.mtocp.ui.model.statedtoui.CoachUi

class CreatePassengerCoachUseCase(
    private val getDepotByNameUseCase: GetDepotByNameUseCase
) {
    suspend fun invoke(
        coachUI: CoachUi
    ): PassengerCar {
        val depotDomain = getDepotByNameUseCase(coachUI.depot, false)
        val coachDomain = coachUI.toDomain(depotDomain)

        if (coachUI.workerId != null) {
            coachUI.workerDepot?.let {
                val workerDomainDepot = getDepotByNameUseCase(coachUI.workerDepot, false)
                val workerDomain = InnerWorkerDomain(
                    coachUI.workerId.toInt(),
                    coachUI.workerName,
                    workerDomainDepot,
                    WorkerTypes.fromString(coachUI.workerPosition)
                )
                coachDomain.worker = workerDomain
            }
        }
        return coachDomain
    }
}