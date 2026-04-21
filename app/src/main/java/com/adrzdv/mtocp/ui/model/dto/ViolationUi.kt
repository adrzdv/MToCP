package com.adrzdv.mtocp.ui.model.dto

data class ViolationUi(
    val code: Int,
    val description: String,
    val shortDescription: String,
    val value: Int,
    val isResolved: Boolean,
    val attributes: List<String> = emptyList(),
    val mediaPaths: List<String> = emptyList()
)