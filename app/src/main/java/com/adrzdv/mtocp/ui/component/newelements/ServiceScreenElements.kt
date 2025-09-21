package com.adrzdv.mtocp.ui.component.newelements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun SettingsRow(
    icon: Painter,
    title: String,
    subtitle: String? = null,
    isClickable: Boolean? = null,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = isClickable == true,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = ripple()
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = icon, contentDescription = null, tint = AppColors.SURFACE_COLOR.color)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = AppColors.SURFACE_COLOR.color,
                style = AppTypography.bodyLarge
            )
            subtitle?.let {
                Text(
                    text = it,
                    color = AppColors.SURFACE_COLOR.color,
                    style = AppTypography.bodySmall
                )
            }
        }
        if (isClickable == true) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = AppColors.SURFACE_COLOR.color
            )
        }
    }
}

@Composable
fun SwitchRow(
    icon: Painter,
    title: String,
    subtitle: String?,
    initialState: Boolean,
    onStateChange: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(initialState) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            tint = AppColors.SURFACE_COLOR.color,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = AppTypography.bodyLarge,
                color = AppColors.SURFACE_COLOR.color
            )
            subtitle?.let {
                Text(
                    text = it,
                    style = AppTypography.bodySmall,
                    color = AppColors.SURFACE_COLOR.color
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                onStateChange(it)
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = AppColors.SECONDARY_COLOR.color,
            )
        )
    }
}

@Composable
fun Divider() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.5f),
            thickness = 1.dp,
            color = AppColors.SURFACE_COLOR.color
        )
    }
}