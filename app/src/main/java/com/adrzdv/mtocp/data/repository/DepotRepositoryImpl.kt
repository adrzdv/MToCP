package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.db.dao.DepotDao
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.domain.repository.DepotRepository

class DepotRepositoryImpl(
    val dao: DepotDao
) : DepotRepository {

    override suspend fun getAll(): List<DepotWithBranch> {
        return dao.depots
    }

    override suspend fun getEntity(id: Int): DepotWithBranch {
        return dao.getDepotById(id)
    }

    override suspend fun saveAll(entities: List<DepotWithBranch>) {
        for (entity in entities) {
            dao.insertDepotWithBranch(entity.depot, entity.branch)
        }
    }

    override suspend fun getDinnerDepots(): List<DepotWithBranch> {
        return dao.dinnerDepots
    }

}