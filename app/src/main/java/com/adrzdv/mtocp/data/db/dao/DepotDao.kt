package com.adrzdv.mtocp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import androidx.room.Update
import com.adrzdv.mtocp.data.db.entity.DepotEntity
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import kotlinx.coroutines.flow.Flow

@Dao
interface DepotDao {

    @Transaction
    @Query("SELECT * FROM depots WHERE is_active = 1")
    fun getAllDepotsWithBranches(): Flow<List<DepotWithBranch>>

    @Transaction
    @Query("SELECT * FROM depots WHERE is_active = 1")
    suspend fun getAllDepotsWithBranchesList(): List<DepotWithBranch>

    @Transaction
    @Query("SELECT * FROM depots WHERE id = :id")
    suspend fun getDepotById(id: Int): DepotWithBranch?

    @Transaction
    @Query("SELECT * FROM depots WHERE branch_id = :branchId AND is_active = 1")
    fun getDepotsByBranch(branchId: Int): Flow<List<DepotWithBranch>>

    @Query("UPDATE depots SET is_active = 0")
    suspend fun deactivateAllDepots()

    @Upsert
    suspend fun insertDepots(depots: List<DepotEntity>)

    @Update
    suspend fun updateDepot(depot: DepotEntity)

    @Query("UPDATE depots SET is_active = 0 WHERE id = :id")
    suspend fun softDeleteDepot(id: Int)

    @Transaction
    @Query(
        """
        SELECT * FROM depots 
        WHERE (full_name LIKE '%' || :query || '%' OR short_name LIKE '%' || :query || '%')
        AND is_active = 1
    """
    )
    fun searchDepots(query: String): Flow<List<DepotWithBranch>>
}
