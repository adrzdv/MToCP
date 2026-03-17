package com.adrzdv.mtocp.domain.usecase

import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain
import com.adrzdv.mtocp.mapper.toDomain
import com.adrzdv.mtocp.ui.model.dto.CoachUi

/**
 * Use case responsible for transforming a passenger coach UI model into a domain model.
 *
 * This class handles the orchestration of retrieving necessary domain entities (like Depots)
 * based on the names provided in the UI model and mapping nested worker information if available.
 *
 * @property getDepotByNameUseCase Use case to fetch depot domain data by its name.
 */
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