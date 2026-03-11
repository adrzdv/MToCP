package com.adrzdv.mtocp.data.repository.refcache

import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.data.db.entity.ViolationEntity

class CacheRepository(
    val appDependencies: AppDependencies
) {
    val violationCache = ReferencesCache<Int, ViolationEntity>()

    fun loadCache() {
        val violationsMut =
            appDependencies.violationRepo.all
                .toList()
        violationCache.set(violationsMut) { it.code }
    }
}