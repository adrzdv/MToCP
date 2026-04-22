package com.adrzdv.mtocp.data.model.document

data class PaginationResponseDto<T>(
    val entities: List<T>,
    val total: Int
)
