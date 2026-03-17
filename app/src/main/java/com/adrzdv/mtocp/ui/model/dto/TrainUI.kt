package com.adrzdv.mtocp.ui.model.dto

data class TrainUI(
    val number: String,
    val route: String,
    val depot: String,
    val branch: String,
    val isProgressive: Boolean? = false,
    val isCCTV: Boolean? = false
)
