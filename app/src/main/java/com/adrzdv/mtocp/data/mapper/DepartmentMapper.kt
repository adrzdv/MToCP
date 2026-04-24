package com.adrzdv.mtocp.data.mapper

import com.adrzdv.mtocp.data.db.entity.DepartmentEntity
import com.adrzdv.mtocp.data.model.dto.DepartmentDto

fun DepartmentDto.toEntity() = DepartmentEntity(
    id = id,
    shortName = shortName,
    fullName = fullName,
    isActive = isActive
)