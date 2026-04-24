package com.adrzdv.mtocp.data.mapper

import com.adrzdv.mtocp.data.db.entity.DivisionEntity
import com.adrzdv.mtocp.data.model.dto.DivisionDto

fun DivisionDto.toEntity() = DivisionEntity(
    id = id,
    shortName = shortName,
    name = name,
    isActive = isActive
)