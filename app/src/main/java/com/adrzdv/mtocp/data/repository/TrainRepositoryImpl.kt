package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.db.dao.DepotDao
import com.adrzdv.mtocp.data.db.dao.TrainDao
import com.adrzdv.mtocp.data.db.entity.TrainEntity
import com.adrzdv.mtocp.data.db.pojo.TrainWithDepotAndBranch
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain
import com.adrzdv.mtocp.domain.repository.TrainRepository
import com.adrzdv.mtocp.mapper.DepotMapper
import com.adrzdv.mtocp.mapper.TrainMapper

class TrainRepositoryImpl(
    val dao: TrainDao,
    val depotDao: DepotDao
) : TrainRepository {
    override suspend fun getAll(): List<TrainEntity> {
        return dao.all
    }

    override suspend fun getEntity(id: Int): TrainEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainByNumber(str: String): TrainDomain {
        val entity = dao.getTrainByString(str)
        val depotPojo = depotDao.getDepotById(entity.depotId)
        val depotDomain = DepotMapper.fromJoinModelToDomain(depotPojo)
        return TrainMapper.fromEntityToDomain(entity, depotDomain)
    }

    override suspend fun saveAll(entities: List<TrainEntity>) {
        dao.insertAll(entities)
    }

    override suspend fun getTrainWithAllData(): List<TrainWithDepotAndBranch> {
        return dao.trainWithFullData
    }
}