package com.adrzdv.mtocp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import androidx.room.Update
import com.adrzdv.mtocp.data.db.entity.BranchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BranchDao {

    @Query("SELECT * FROM branches WHERE is_active = 1 ORDER BY short_name ASC")
    fun getAllBranches(): Flow<List<BranchEntity>>

    @Query("SELECT * FROM branches WHERE id = :id LIMIT 1")
    suspend fun getBranchById(id: Int): BranchEntity?

    @Query("UPDATE branches SET is_active = 0")
    suspend fun deactivateAllBranches()

    @Upsert
    suspend fun insertBranches(branches: List<BranchEntity>)

    @Upsert
    suspend fun insertBranch(branch: BranchEntity)

    @Update
    suspend fun updateBranch(branch: BranchEntity)
    
    @Query("UPDATE branches SET is_active = 0 WHERE id = :id")
    suspend fun softDeleteBranch(id: Int)
}
