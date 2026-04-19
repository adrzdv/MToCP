package com.adrzdv.mtocp.domain.repository

import com.adrzdv.mtocp.data.db.entity.ViolationEntity
import com.adrzdv.mtocp.domain.model.enums.RevisionType

interface ViolationRepository : BaseAppRepository<ViolationEntity, Int> {
    suspend fun getFilteredByRevisionType(type: RevisionType?)
}