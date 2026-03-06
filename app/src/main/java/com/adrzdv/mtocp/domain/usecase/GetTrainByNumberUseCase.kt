package com.adrzdv.mtocp.domain.usecase

import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain
import com.adrzdv.mtocp.domain.repository.TrainRepository

class GetTrainByNumberUseCase(
    private val trainRepository: TrainRepository
) {
    suspend fun invoke(number: String): TrainDomain {
        return trainRepository.getTrainByNumber(number)
    }
}