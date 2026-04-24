package com.adrzdv.mtocp.data.mapper

import com.adrzdv.mtocp.data.db.entity.RevisionTypeEntity
import com.adrzdv.mtocp.data.model.dto.RevisionTypeDto

fun RevisionTypeDto.toEntity() = RevisionTypeEntity(
    id = id,
    name = name
)