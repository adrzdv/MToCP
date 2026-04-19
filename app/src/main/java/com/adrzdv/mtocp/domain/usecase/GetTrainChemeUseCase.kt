package com.adrzdv.mtocp.domain.usecase

import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain

class GetTrainSchemeUseCase {
    fun invoke(train: TrainDomain): Map<String, Int> {
        val res = train.objectsMap.values
            .filterIsInstance<PassengerCar>()
            .groupingBy { it.coachType.passengerCoachTitle }
            .eachCount()
            .toMutableMap()

        if (train.dinnerCar != null) {
            res[train.dinnerCar.dinnerType.description] = 1
        }

        return res
    }

}