package com.adrzdv.mtocp.ui.component.buttons

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SplitButton(
    actions: Map<String, Pair<Painter, () -> Unit>>,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedAction by remember { mutableStateOf(actions.keys.first()) }

    Box {
        SplitButtonLayout(
            leadingButton = {
                SplitButtonDefaults.LeadingButton(
                    onClick = {
                        actions[selectedAction]?.second?.invoke()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.MAIN_COLOR.color,
                        contentColor = AppColors.SURFACE_COLOR.color,
                    ),
                    enabled = enabled
                ) {
                    actions[selectedAction]?.first?.let {
                        Icon(
                            painter = it,
                            tint = AppColors.SURFACE_COLOR.color,
                            contentDescription = null
                        )
                    }
                    actions[selectedAction]?.first
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        text = selectedAction,
                        style = AppTypography.bodyMedium
                    )
                }
            },
            trailingButton = {
                Box {
                    SplitButtonDefaults.TrailingButton(
                        enabled = enabled,
                        checked = expanded,
                        onCheckedChange = { expanded = it },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.MAIN_COLOR.color,
                            contentColor = AppColors.SURFACE_COLOR.color
                        )
                    ) {
                        val rotation: Float by
                        animateFloatAsState(
                            targetValue = if (expanded) 180f else 0f
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier
                                .size(SplitButtonDefaults.TrailingIconSize)
                                .graphicsLayer {
                                    this.rotationZ = rotation
                                }
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.align(Alignment.TopEnd),
                        containerColor = AppColors.SURFACE_COLOR.color
                    ) {
                        actions.keys.forEach { item ->
                            DropdownMenuItem(
                                leadingIcon = {
                                    actions[item]?.first?.let {
                                        Icon(
                                            painter = it,
                                            tint = AppColors.MAIN_COLOR.color,
                                            contentDescription = null
                                        )
                                    }
                                },
                                text = { Text(text = item, style = AppTypography.bodyMedium) },
                                onClick = {
                                    selectedAction = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}



