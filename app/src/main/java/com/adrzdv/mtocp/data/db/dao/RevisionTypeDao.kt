package com.adrzdv.mtocp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.adrzdv.mtocp.data.db.entity.RevisionTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RevisionTypeDao {
    @Query("SELECT * FROM revision_types ORDER BY name ASC")
    fun getAllRevisionTypes(): Flow<List<RevisionTypeEntity>>

    @Query("DELETE FROM revision_types")
    suspend fun clearAllRevisionTypes()

    @Upsert
    suspend fun insertRevisionTypes(types: List<RevisionTypeEntity>)
}
