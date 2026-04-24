package com.adrzdv.mtocp.data.model.dto

data class BranchDto(
    val id: Int,
    val shortName: String,
    val fullName: String,
    val active: Boolean?
)
