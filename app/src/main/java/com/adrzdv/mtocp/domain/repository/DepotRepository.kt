package com.adrzdv.mtocp.domain.repository

import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch

interface DepotRepository : BaseAppRepository<DepotWithBranch, Int> {
    suspend fun getDinnerDepots(): List<DepotWithBranch>
}