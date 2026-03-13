package com.adrzdv.mtocp.domain.usecase

import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain

class GetTrainSchemeUseCase {
    fun invoke(train: TrainDomain): Map<String, Int> =
        train.objectsMap.values
            .map { it as PassengerCar }
            .groupingBy { it.coachType.passengerCoachTitle }
            .eachCount()
}