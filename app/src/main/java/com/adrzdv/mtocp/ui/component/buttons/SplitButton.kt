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
import androidx.compose.ui.res.stringResource
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SplitButton(
    onAddCoachClick: () -> Unit,
    onAddDinnerClick: () -> Unit,
    onClearListClick: () -> Unit,
    addCoachIcon: Painter,
    addDinnerIcon: Painter,
    clearIcon: Painter
) {
    var expanded by remember { mutableStateOf(false) }
    val addCoach = stringResource(R.string.add_string)
    val addDinner = stringResource(R.string.dinner_add)
    val clearList = stringResource(R.string.clear_text)
    var selectedAction by remember { mutableStateOf(addCoach) }
    val actions: Map<String, Pair<Painter, () -> Unit>> = mapOf(
        addCoach to Pair(addCoachIcon, onAddCoachClick),
        addDinner to Pair(addDinnerIcon, onAddDinnerClick),
        clearList to Pair(clearIcon, onClearListClick)
    )
    Box {
        SplitButtonLayout(
            leadingButton = {
                SplitButtonDefaults.LeadingButton(
                    onClick = {
                        actions[selectedAction]?.second?.invoke()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.MAIN_GREEN.color,
                        contentColor = AppColors.OFF_WHITE.color
                    )
                ) {
                    actions[selectedAction]?.first?.let {
                        Icon(
                            painter = it,
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
                        checked = expanded,
                        onCheckedChange = { expanded = it },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.MAIN_GREEN.color,
                            contentColor = AppColors.OFF_WHITE.color
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
                        containerColor = AppColors.OFF_WHITE.color
                    ) {
                        actions.keys.forEach { item ->
                            DropdownMenuItem(
                                leadingIcon = {
                                    actions[item]?.first?.let {
                                        Icon(
                                            painter = it,
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



