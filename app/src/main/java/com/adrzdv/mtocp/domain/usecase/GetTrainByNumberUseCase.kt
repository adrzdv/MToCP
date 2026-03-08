package com.adrzdv.mtocp.domain.usecase

import android.util.Log
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain
import com.adrzdv.mtocp.domain.repository.TrainRepository

class GetTrainByNumberUseCase(
    private val trainRepository: TrainRepository
) {
    suspend operator fun invoke(number: String): TrainDomain {
        try {
            return trainRepository.getTrainByNumber(number)
        } catch (e: Exception) {
            Log.e("GetTrainByNumberUseCase", "Error getting train by number: $number", e)
            throw e
        }
    }
}