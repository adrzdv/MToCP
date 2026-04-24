package com.adrzdv.mtocp.data.mapper

import com.adrzdv.mtocp.data.db.entity.ViolationDepartmentCrossRef
import com.adrzdv.mtocp.data.db.entity.ViolationDivisionCrossRef
import com.adrzdv.mtocp.data.db.entity.ViolationEntity
import com.adrzdv.mtocp.data.db.entity.ViolationTypeCrossRef
import com.adrzdv.mtocp.data.model.dto.ViolationFullDto

fun ViolationFullDto.toEntity() = ViolationEntity(
    id = id,
    code = code,
    description = description,
    criteria = criteria,
    measure = measure,
    isActive = isActive
)

fun ViolationFullDto.toDivisionRefs(): List<ViolationDivisionCrossRef> =
    divisions.map { division ->
        ViolationDivisionCrossRef(
            divisionId = division.id,
            violationId = id
        )
    }

fun ViolationFullDto.toDepartmentRefs(): List<ViolationDepartmentCrossRef> =
    departments.map { department ->
        ViolationDepartmentCrossRef(
            violationId = id,
            departmentId = department.id
        )
    }

fun ViolationFullDto.toTypeRefs(): List<ViolationTypeCrossRef> =
    types.map { type ->
        ViolationTypeCrossRef(
            violationId = id,
            typeId = type.id
        )
    }