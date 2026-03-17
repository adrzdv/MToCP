package com.adrzdv.mtocp.domain.usecase

import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar
import com.adrzdv.mtocp.mapper.toDomain
import com.adrzdv.mtocp.ui.model.statedtoui.DinnerCarUI

class CreateDinnerCarUseCase(
    private val getDepotByNameUseCase: GetDepotByNameUseCase,
    private val getCompanyByNameUseCase: GetCompanyByNameUseCase
) {
    suspend fun invoke(dinnerCoach: DinnerCarUI): DinnerCar {
        if (dinnerCoach.depot.isNotEmpty()) {
            val depotDomain = getDepotByNameUseCase(dinnerCoach.depot, true)
            return dinnerCoach.toDomain(depotDomain)
        } else {
            val companyDomain = getCompanyByNameUseCase(dinnerCoach.company)
            return dinnerCoach.toDomain(company = companyDomain)
        }
    }
}