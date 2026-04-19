package com.adrzdv.mtocp.ui.component.newelements

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun AppCheckBox(
    checked: Boolean = false,
    label: String,
    onChange: () -> Unit
) {
    Checkbox(
        checked = checked,
        colors = CheckboxDefaults.colors(
            checkedColor = AppColors.MAIN_COLOR.color,
            uncheckedColor = AppColors.MAIN_COLOR.color,
            checkmarkColor = AppColors.SURFACE_COLOR.color
        ),
        onCheckedChange = {
            onChange()
        },
        enabled = true
    )
    Text(
        text = label,
        color = AppColors.MAIN_COLOR.color,
        style = AppTypography.labelSmall
    )
}