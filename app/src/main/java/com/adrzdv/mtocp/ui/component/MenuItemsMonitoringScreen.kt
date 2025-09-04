package com.adrzdv.mtocp.ui.component

enum class MonitoringMenuItem {
    CHECK_ADD_PARAMS,
    CHECK_DINING_CAR,
    EXPORT_DATA
}

data class MenuElementData(
    val name: MonitoringMenuItem,
    val iconRes: Int,
    val onClick: () -> Unit,
    val isEnable: Boolean,
    val description: String
)