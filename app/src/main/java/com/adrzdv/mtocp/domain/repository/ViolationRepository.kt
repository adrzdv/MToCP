package com.adrzdv.mtocp.domain.repository

import com.adrzdv.mtocp.data.db.entity.ViolationDepartmentCrossRef
import com.adrzdv.mtocp.data.db.entity.ViolationDivisionCrossRef
import com.adrzdv.mtocp.data.db.entity.ViolationEntity
import com.adrzdv.mtocp.data.db.entity.ViolationFullInfo
import com.adrzdv.mtocp.data.db.entity.ViolationTypeCrossRef

interface ViolationRepository : BaseAppRepository<ViolationEntity, Int> {
    suspend fun getAllFull(): List<ViolationFullInfo>

    suspend fun saveViolation(
        violation: ViolationEntity,
        departmentIds: List<Int>,
        divisionIds: List<Int>,
        typeIds: List<Int>
    )

    suspend fun syncViolations(
        violations: List<ViolationEntity>,
        departmentLinks: List<ViolationDepartmentCrossRef>,
        divisionLinks: List<ViolationDivisionCrossRef>,
        typeLinks: List<ViolationTypeCrossRef>
    )

    suspend fun clearAllViolationData()

    suspend fun delete(id: Int)
}
