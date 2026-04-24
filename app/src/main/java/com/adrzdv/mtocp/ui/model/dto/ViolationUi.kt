package com.adrzdv.mtocp.ui.model.dto

data class ViolationUi(
    val code: Int,
    val description: String,
    val shortDescription: String,
    val value: Int,
    val isResolved: Boolean,
    val criteria: String = "",
    val measure: String = "",
    val revisionTypes: List<String> = emptyList(),
    val departments: List<String> = emptyList(),
    val divisions: List<String> = emptyList(),
    val attributes: List<String> = emptyList(),
    val mediaPaths: List<String> = emptyList()
)
