package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.db.dao.ViolationDao
import com.adrzdv.mtocp.data.db.entity.ViolationEntity
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.domain.repository.ViolationRepository

class ViolationRepositoryImpl(
    val dao: ViolationDao
) : ViolationRepository {
    override suspend fun getAll(): List<ViolationEntity> {
        return dao.all
    }

    override suspend fun getEntity(id: Int): ViolationEntity {
        TODO("Not necessary to use")
    }

    override suspend fun saveAll(entities: List<ViolationEntity>) {
        dao.insertAll(entities)
    }

    override suspend fun getFilteredByRevisionType(type: RevisionType?) {
        when (type) {
            RevisionType.IN_TRANSIT -> dao.all.filter { it.inTransit }
            RevisionType.AT_START_POINT -> dao.all.filter { it.atStartPoint }
            RevisionType.AT_TURNROUND_POINT -> dao.all.filter { it.atStartPoint }
            RevisionType.AT_TICKET_OFFICE -> dao.all.filter { it.atTicketOffice }
            else -> dao.all
        }

    }

}