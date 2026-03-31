package com.adrzdv.mtocp.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.adrzdv.mtocp.data.db.entity.KriCoachEntity

@Dao
interface KriCoachDao {

    @Transaction
    @Query("SELECT * FROM coaches")
    suspend fun getAllKriCoaches(): List<KriCoachEntity>

}