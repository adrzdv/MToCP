package com.adrzdv.mtocp.data.model.dto

data class DepotDto(
    val id: Int,
    val shortName: String,
    val fullName: String,
    val active: Boolean?,
    val branchId: Int,
    val phoneNumber: String
)
