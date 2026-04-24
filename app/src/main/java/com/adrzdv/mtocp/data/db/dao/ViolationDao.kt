package com.adrzdv.mtocp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.adrzdv.mtocp.data.db.entity.ViolationDepartmentCrossRef
import com.adrzdv.mtocp.data.db.entity.ViolationDivisionCrossRef
import com.adrzdv.mtocp.data.db.entity.ViolationEntity
import com.adrzdv.mtocp.data.db.entity.ViolationFullInfo
import com.adrzdv.mtocp.data.db.entity.ViolationTypeCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface ViolationDao {

    @Query("SELECT * FROM violations WHERE is_active = 1")
    suspend fun getAll(): List<ViolationEntity>

    @Transaction
    @Query("SELECT * FROM violations WHERE is_active = 1")
    fun getAllFullViolations(): Flow<List<ViolationFullInfo>>

    @Transaction
    @Query("SELECT * FROM violations WHERE is_active = 1")
    suspend fun getAllFullViolationsList(): List<ViolationFullInfo>

    @Transaction
    @Query("SELECT * FROM violations WHERE id = :id")
    suspend fun getViolationById(id: Int): ViolationFullInfo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViolation(violation: ViolationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViolations(violations: List<ViolationEntity>)

    @Update
    suspend fun updateViolation(violation: ViolationEntity)

    @Transaction
    suspend fun updateFullViolation(
        violation: ViolationEntity,
        departmentIds: List<Int>,
        divisionIds: List<Int>,
        typeIds: List<Int>
    ) {
        updateViolation(violation)

        clearDepartmentLinks(violation.id)
        insertDepartmentLinks(departmentIds.map { ViolationDepartmentCrossRef(violation.id, it) })

        clearDivisionLinks(violation.id)
        insertDivisionLinks(divisionIds.map { ViolationDivisionCrossRef(it, violation.id) })

        clearTypeLinks(violation.id)
        insertTypeLinks(typeIds.map { ViolationTypeCrossRef(violation.id, it) })
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDepartmentLinks(links: List<ViolationDepartmentCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDivisionLinks(links: List<ViolationDivisionCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypeLinks(links: List<ViolationTypeCrossRef>)

    @Query("DELETE FROM violations_departments WHERE violation_id = :violationId")
    suspend fun clearDepartmentLinks(violationId: Int)

    @Query("DELETE FROM violations_divisions WHERE violation_id = :violationId")
    suspend fun clearDivisionLinks(violationId: Int)

    @Query("DELETE FROM violations_types WHERE violation_id = :violationId")
    suspend fun clearTypeLinks(violationId: Int)

    @Query("DELETE FROM violations_departments")
    suspend fun clearAllDepartmentLinks()

    @Query("DELETE FROM violations_divisions")
    suspend fun clearAllDivisionLinks()

    @Query("DELETE FROM violations_types")
    suspend fun clearAllTypeLinks()

    @Query("DELETE FROM violations")
    suspend fun clearAllViolations()

    @Transaction
    suspend fun clearAllViolationData() {
        clearAllDepartmentLinks()
        clearAllDivisionLinks()
        clearAllTypeLinks()
        clearAllViolations()
    }

    @Transaction
    suspend fun replaceAllViolations(
        violations: List<ViolationEntity>,
        departmentLinks: List<ViolationDepartmentCrossRef>,
        divisionLinks: List<ViolationDivisionCrossRef>,
        typeLinks: List<ViolationTypeCrossRef>
    ) {
        clearAllViolationData()

        insertViolations(violations)

        if (departmentLinks.isNotEmpty()) {
            insertDepartmentLinks(departmentLinks)
        }
        if (divisionLinks.isNotEmpty()) {
            insertDivisionLinks(divisionLinks)
        }
        if (typeLinks.isNotEmpty()) {
            insertTypeLinks(typeLinks)
        }
    }

    @Delete
    suspend fun deleteViolation(violation: ViolationEntity)

    @Query("UPDATE violations SET is_active = 0 WHERE id = :id")
    suspend fun softDeleteViolation(id: Int)

    @Transaction
    @Query(
        """
    SELECT * FROM violations 
    WHERE (description LIKE '%' || :query || '%' OR code LIKE '%' || :query || '%') 
    AND is_active = 1
    """
    )
    fun searchViolations(query: String): Flow<List<ViolationFullInfo>>

    @Transaction
    @Query(
        """
    SELECT * FROM violations 
    WHERE id IN (
        SELECT violation_id FROM violations_departments WHERE department_id = :deptId
    ) AND is_active = 1
    """
    )
    fun getViolationsByDepartment(deptId: Int): Flow<List<ViolationFullInfo>>

}
