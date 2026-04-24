package com.adrzdv.mtocp.data.model.dto

data class ViolationFullDto(
    val id: Int,
    val code: Int,
    val description: String,
    val criteria: String,
    val measure: String,
    val isActive: Boolean,
    val departments: List<DepartmentDto>,
    val types: List<RevisionTypeDto>,
    val divisions: List<DivisionShortDto>
)