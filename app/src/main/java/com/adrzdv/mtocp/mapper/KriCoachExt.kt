package com.adrzdv.mtocp.mapper

import com.adrzdv.mtocp.data.db.entity.KriCoachEntity
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.KriCoachDomain
import com.adrzdv.mtocp.ui.model.KriCoachDto

fun KriCoachEntity.toDomain() = KriCoachDomain(id, number)
fun KriCoachDomain.toDto() = KriCoachDto(id, number)
