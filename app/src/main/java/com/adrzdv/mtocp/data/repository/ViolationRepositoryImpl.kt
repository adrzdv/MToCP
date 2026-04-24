package com.adrzdv.mtocp.data.repository

import com.adrzdv.mtocp.data.db.dao.ViolationDao
import com.adrzdv.mtocp.data.db.entity.ViolationDepartmentCrossRef
import com.adrzdv.mtocp.data.db.entity.ViolationDivisionCrossRef
import com.adrzdv.mtocp.data.db.entity.ViolationEntity
import com.adrzdv.mtocp.data.db.entity.ViolationFullInfo
import com.adrzdv.mtocp.data.db.entity.ViolationTypeCrossRef
import com.adrzdv.mtocp.domain.repository.ViolationRepository
import kotlinx.coroutines.flow.Flow

class ViolationRepositoryImpl(private val violationDao: ViolationDao) : ViolationRepository {

    val allViolations: Flow<List<ViolationFullInfo>> = violationDao.getAllFullViolations()

    fun search(query: String) = violationDao.searchViolations(query)

    override suspend fun getAll(): List<ViolationEntity> = violationDao.getAll()

    override suspend fun getEntity(id: Int): ViolationEntity {
        return violationDao.getViolationById(id)?.violation
            ?: throw IllegalArgumentException("Violation with id=$id not found")
    }

    override suspend fun saveAll(entities: List<ViolationEntity>) {
        violationDao.insertViolations(entities)
    }

    override suspend fun getAllFull(): List<ViolationFullInfo> =
        violationDao.getAllFullViolationsList()

    override suspend fun saveViolation(
        violation: ViolationEntity,
        departmentIds: List<Int>,
        divisionIds: List<Int>,
        typeIds: List<Int>
    ) {
        if (violation.id == 0) {
            val newId = violationDao.insertViolation(violation).toInt()

            violationDao.updateFullViolation(
                violation.copy(id = newId),
                departmentIds,
                divisionIds,
                typeIds
            )
        } else {

            violationDao.updateFullViolation(violation, departmentIds, divisionIds, typeIds)
        }
    }

    override suspend fun syncViolations(
        violations: List<ViolationEntity>,
        departmentLinks: List<ViolationDepartmentCrossRef>,
        divisionLinks: List<ViolationDivisionCrossRef>,
        typeLinks: List<ViolationTypeCrossRef>
    ) {
        violationDao.replaceAllViolations(
            violations = violations,
            departmentLinks = departmentLinks,
            divisionLinks = divisionLinks,
            typeLinks = typeLinks
        )
    }

    override suspend fun clearAllViolationData() {
        violationDao.clearAllViolationData()
    }

    override suspend fun delete(id: Int) {
        violationDao.softDeleteViolation(id)
    }
}
