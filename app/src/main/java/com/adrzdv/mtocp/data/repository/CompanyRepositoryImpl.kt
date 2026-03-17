package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.db.dao.CompanyDao
import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch
import com.adrzdv.mtocp.domain.repository.CompanyRepository
import com.adrzdv.mtocp.mapper.CompanyMapper

class CompanyRepositoryImpl(
    val dao: CompanyDao
) : CompanyRepository {
    override suspend fun getAll(): List<CompanyWithBranch> {
        return dao.all
    }

    override suspend fun getEntity(id: Int): CompanyWithBranch {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(entities: List<CompanyWithBranch>) {
        val companyEntities = entities.stream()
            .map { CompanyMapper.fromPojoToEntity(it) }
            .toList()
        dao.insertAll(companyEntities)
    }

}