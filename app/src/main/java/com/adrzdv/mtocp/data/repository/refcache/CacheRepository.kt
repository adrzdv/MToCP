package com.adrzdv.mtocp.data.repository.refcache

import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.data.db.entity.ViolationFullInfo

class CacheRepository(
    val appDependencies: AppDependencies
) {
    val violationCache = ReferencesCache<Int, ViolationFullInfo>()
    val depotCache = ReferencesCache<Int, DepotWithBranch>()

    suspend fun loadCache() {
        val violations = appDependencies.violationRepo.getAllFull()
        val depots = appDependencies.depotRepo.getAll()

        violationCache.set(violations) { it.violation.code }
        depotCache.set(depots) { it.depot.id }
    }
}
