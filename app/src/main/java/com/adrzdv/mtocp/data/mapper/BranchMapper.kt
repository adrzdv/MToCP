package com.adrzdv.mtocp.data.mapper

import com.adrzdv.mtocp.data.db.entity.BranchEntity
import com.adrzdv.mtocp.data.model.dto.BranchDto

fun BranchDto.toEntity() = BranchEntity(
    id = id,
    shortName = shortName,
    fullName = fullName,
    isActive = active ?: true
)
