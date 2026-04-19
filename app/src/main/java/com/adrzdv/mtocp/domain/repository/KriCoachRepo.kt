package com.adrzdv.mtocp.domain.repository

import com.adrzdv.mtocp.data.db.entity.KriCoachEntity

interface KriCoachRepo : BaseAppRepository<KriCoachEntity, String> {
    override suspend fun getAll(): List<KriCoachEntity>
    override suspend fun getEntity(id: String): KriCoachEntity
}