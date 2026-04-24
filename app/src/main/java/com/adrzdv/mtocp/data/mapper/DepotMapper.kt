package com.adrzdv.mtocp.data.mapper

import com.adrzdv.mtocp.data.db.entity.DepotEntity
import com.adrzdv.mtocp.data.model.dto.DepotDto

fun DepotDto.toEntity() = DepotEntity(
    id = id,
    shortName = shortName,
    fullName = fullName,
    isActive = active ?: true,
    branchId = branchId,
    phoneNumber = phoneNumber
)
