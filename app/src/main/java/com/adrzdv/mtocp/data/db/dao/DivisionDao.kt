package com.adrzdv.mtocp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.adrzdv.mtocp.data.db.entity.DivisionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DivisionDao {
    @Query("SELECT * FROM divisions WHERE is_active = 1 ORDER BY name ASC")
    fun getAllDivisions(): Flow<List<DivisionEntity>>

    @Query("UPDATE divisions SET is_active = 0")
    suspend fun deactivateAllDivisions()

    @Query("DELETE FROM divisions")
    suspend fun clearAllDivisions()

    @Upsert
    suspend fun insertDivisions(divisions: List<DivisionEntity>)
}
