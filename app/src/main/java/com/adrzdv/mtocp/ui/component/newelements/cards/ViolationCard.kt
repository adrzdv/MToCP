package com.adrzdv.mtocp.ui.component.newelements.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.model.dto.ViolationUi
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun ViolationCard(
    violation: ViolationUi,
    onChangeValueClick: (() -> Unit)? = null,
    onResolveClick: (() -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null,
    onMakePhotoClick: (() -> Unit)? = null,
    onMakeVideoClick: (() -> Unit)? = null,
    onAddTagClick: (() -> Unit)? = null,
    onItemClick: ((ViolationUi) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }
    var isResolved by remember { mutableStateOf(violation.isResolved) }
    val hasActions = onChangeValueClick != null ||
            onResolveClick != null ||
            onDeleteClick != null ||
            onMakePhotoClick != null ||
            onMakeVideoClick != null ||
            onAddTagClick != null

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp)
            .border(
                width = 1.dp,
                color = AppColors.MAIN_COLOR.color,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (onItemClick != null && !hasActions) {
                        onItemClick(violation)
                    } else {
                        expanded = !expanded
                    }
                },
            colors = CardDefaults.cardColors(
                containerColor = AppColors.SURFACE_COLOR.color,
                contentColor = AppColors.MAIN_COLOR.color
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        style = AppTypography.bodyMedium,
                        text = violation.code.toString()
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            style = AppTypography.bodyMedium,
                            text = violation.description,
                            maxLines = if (expanded) Int.MAX_VALUE else 3,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (violation.departments.isNotEmpty() || violation.measure.isNotBlank()) {
                            Spacer(modifier = Modifier.padding(top = 6.dp))
                            val summary = buildList {
                                violation.departments.firstOrNull()?.let {
                                    add("${stringResource(R.string.violation_departments_short_label)}: $it")
                                }
                                if (violation.measure.isNotBlank()) {
                                    add("${stringResource(R.string.violation_measure_short_label)}: ${violation.measure}")
                                }
                            }.joinToString(" | ")
                            Text(
                                text = summary,
                                style = AppTypography.bodySmall,
                                color = AppColors.MAIN_COLOR.color.copy(alpha = 0.78f),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        } else if (!expanded && violation.shortDescription.isNotBlank()) {
                            Spacer(modifier = Modifier.padding(top = 4.dp))
                            Text(
                                text = violation.shortDescription,
                                style = AppTypography.bodySmall,
                                color = AppColors.MAIN_COLOR.color.copy(alpha = 0.75f),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    if (hasActions) {
                        Box {
                            IconButton(onClick = { menuExpanded = true }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_more_vert),
                                    contentDescription = null,
                                    tint = AppColors.MAIN_COLOR.color
                                )
                            }
                            DropdownMenu(
                                expanded = menuExpanded,
                                containerColor = AppColors.SURFACE_COLOR.color,
                                onDismissRequest = { menuExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(R.string.change_value)) },
                                    onClick = {
                                        menuExpanded = false
                                        onChangeValueClick?.invoke()
                                    },
                                    enabled = onChangeValueClick != null
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(R.string.resolved)) },
                                    onClick = {
                                        menuExpanded = false
                                        isResolved = !isResolved
                                        onResolveClick?.invoke()
                                    },
                                    enabled = onResolveClick != null
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(R.string.add_tag)) },
                                    onClick = {
                                        menuExpanded = false
                                        onAddTagClick?.invoke()
                                    },
                                    enabled = onAddTagClick != null
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(R.string.add_photo)) },
                                    onClick = {
                                        menuExpanded = false
                                        onMakePhotoClick?.invoke()
                                    },
                                    enabled = onMakePhotoClick != null
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(R.string.add_video)) },
                                    onClick = {
                                        menuExpanded = false
                                        onMakeVideoClick?.invoke()
                                    },
                                    enabled = onMakeVideoClick != null
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(R.string.delete)) },
                                    onClick = {
                                        menuExpanded = false
                                        onDeleteClick?.invoke()
                                    },
                                    enabled = onDeleteClick != null
                                )
                            }
                        }
                    }
                }

                if (expanded) {
                    Spacer(modifier = Modifier.padding(top = 4.dp))
                    ViolationMetaRow(
                        title = stringResource(R.string.violation_criteria_label),
                        value = violation.criteria
                    )
                    ViolationMetaRow(
                        title = stringResource(R.string.violation_measure_label),
                        value = violation.measure
                    )
                    ViolationMetaRow(
                        title = stringResource(R.string.violation_revision_types_label),
                        value = violation.revisionTypes.joinToString()
                    )
                    ViolationMetaRow(
                        title = stringResource(R.string.violation_departments_label),
                        value = violation.departments.joinToString()
                    )
                }
            }
        }
        if (hasActions) {
            Badge(
                containerColor = if (isResolved) AppColors.DARK_GREEN.color else AppColors.MATERIAL_RED.color,
                contentColor = AppColors.OFF_WHITE.color,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-8).dp, y = 8.dp)
            ) {
                Text(if (isResolved) stringResource(R.string.resolved) else stringResource(R.string.unresolved))
            }
        }
    }
}

@Composable
private fun ViolationMetaRow(
    title: String,
    value: String
) {
    if (value.isBlank()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(
                color = AppColors.BACKGROUND_COLOR.color,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            text = title,
            style = AppTypography.labelMedium,
            color = AppColors.MAIN_COLOR.color
        )
        Spacer(modifier = Modifier.padding(top = 2.dp))
        Text(
            text = value,
            style = AppTypography.bodySmall,
            color = AppColors.MAIN_COLOR.color
        )
    }
}
