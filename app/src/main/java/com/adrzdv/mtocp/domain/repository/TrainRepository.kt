package com.adrzdv.mtocp.domain.repository

import com.adrzdv.mtocp.data.db.entity.TrainEntity
import com.adrzdv.mtocp.data.db.pojo.TrainWithDepotAndBranch
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain

interface TrainRepository : BaseAppRepository<TrainEntity, Int> {
    suspend fun getTrainByNumber(nmb: String): TrainDomain
    suspend fun getTrainWithAllData(): List<TrainWithDepotAndBranch>
}