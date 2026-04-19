package com.adrzdv.mtocp.data.repository.refcache

import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.data.db.entity.ViolationEntity

class CacheRepository(
    val appDependencies: AppDependencies
) {
    val violationCache = ReferencesCache<Int, ViolationEntity>()

    suspend fun loadCache() {
        val violations =
            appDependencies.violationRepo.getAll()
                .toList()
        violationCache.set(violations) { it.code }
    }
}