package com.adrzdv.mtocp.data.model.document

data class DocumentRequestDto(
    val username: String,
    val userId: Int?,
    val prefix: String?
)
