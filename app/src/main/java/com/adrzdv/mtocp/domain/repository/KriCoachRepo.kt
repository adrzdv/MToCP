package com.adrzdv.mtocp.domain.repository

import com.adrzdv.mtocp.data.db.entity.KriCoachEntity

interface KriCoachRepo {

    suspend fun getAllKriCoaches(): List<KriCoachEntity>
}