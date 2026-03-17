package com.adrzdv.mtocp.domain.usecase

import com.adrzdv.mtocp.domain.model.enums.WorkerTypes
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain
import com.adrzdv.mtocp.domain.model.workers.OuterWorkerDomain
import com.adrzdv.mtocp.mapper.toDomain
import com.adrzdv.mtocp.ui.model.dto.DinnerCarUI

class CreateDinnerCarUseCase(
    private val getDepotByNameUseCase: GetDepotByNameUseCase,
    private val getCompanyByNameUseCase: GetCompanyByNameUseCase
) {
    suspend fun invoke(dinnerCoach: DinnerCarUI): DinnerCar {
        val dinnerCarDomain: DinnerCar

        if (dinnerCoach.depot.isNotEmpty()) {
            val depotDomain = getDepotByNameUseCase(dinnerCoach.depot, true)
            val worker = InnerWorkerDomain(
                dinnerCoach.workerId?.toIntOrNull() ?: 0,
                dinnerCoach.workerName,
                depotDomain,
                WorkerTypes.fromString(dinnerCoach.workerPosition)
            )
            dinnerCarDomain = dinnerCoach.toDomain(depotDomain)
            dinnerCarDomain.workerDomain = worker

        } else {
            val companyDomain = getCompanyByNameUseCase(dinnerCoach.company)
            val worker = OuterWorkerDomain(
                dinnerCoach.workerId?.toIntOrNull() ?: 0,
                dinnerCoach.workerName,
                companyDomain,
                WorkerTypes.fromString(dinnerCoach.workerPosition)
            )
            dinnerCarDomain = dinnerCoach.toDomain(company = companyDomain)
            dinnerCarDomain.workerDomain = worker
        }

        return dinnerCarDomain
    }
}