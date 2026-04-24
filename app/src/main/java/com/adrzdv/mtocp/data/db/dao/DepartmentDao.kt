package com.adrzdv.mtocp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.adrzdv.mtocp.data.db.entity.DepartmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {
    @Query("SELECT * FROM departments WHERE is_active = 1 ORDER BY short_name ASC")
    fun getAllDepartments(): Flow<List<DepartmentEntity>>

    @Query("UPDATE departments SET is_active = 0")
    suspend fun deactivateAllDepartments()

    @Query("DELETE FROM departments")
    suspend fun clearAllDepartments()

    @Upsert
    suspend fun insertDepartments(departments: List<DepartmentEntity>)

    @Query("SELECT * FROM departments WHERE id = :id")
    suspend fun getDepartmentById(id: Int): DepartmentEntity?
}
