package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.db.dao.KriCoachDao
import com.adrzdv.mtocp.data.db.entity.KriCoachEntity
import com.adrzdv.mtocp.domain.repository.KriCoachRepo

class KriCoachRepoImpl(kriCoachDao: KriCoachDao) : KriCoachRepo {

    private var kriDao: KriCoachDao = kriCoachDao

    override suspend fun getAll(): List<KriCoachEntity> {
        return kriDao.getAllKriCoaches()
            .map { it.copy(number = it.number.trim()) }
    }

    override suspend fun getEntity(id: String): KriCoachEntity {
        return kriDao.getKriCoachByNumber(id)
    }

    override suspend fun saveAll(entities: List<KriCoachEntity>) {
        TODO("Not yet implemented")
    }
}