package com.adrzdv.mtocp.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.Coach
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain
import com.adrzdv.mtocp.domain.model.workers.OuterWorkerDomain
import com.adrzdv.mtocp.ui.model.ViolationDto
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import java.time.format.DateTimeFormatter

@Composable
fun ViolationCard(
    violation: ViolationDto,
    onChangeValueClick: () -> Unit,
    onResolveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onMakePhotoClick: () -> Unit,
    onMakeVideoClick: () -> Unit,
    onAddTagClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var isResolved by remember { mutableStateOf(violation.isResolved) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        expanded = true
                    }
                )
            }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.OFF_WHITE.color,
                contentColor = AppColors.MAIN_GREEN.color
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    style = AppTypography.bodyMedium,
                    text = violation.code.toString()
                )
                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                        .width(8.dp)
                )
                Text(
                    style = AppTypography.bodyMedium,
                    text = violation.name,
                    maxLines = 3,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis
                )
                DropdownMenu(
                    expanded = expanded,
                    containerColor = AppColors.OFF_WHITE.color,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_edit_value_24_white),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.change_value))
                            }
                        },
                        onClick = {
                            expanded = false
                            onChangeValueClick()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_resolved_24_white),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.resolved))
                            }
                        },
                        onClick = {
                            expanded = false
                            isResolved = !isResolved
                            onResolveClick()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add_tag_24_white),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.add_tag))
                            }
                        },
                        onClick = {
                            expanded = false
                            onAddTagClick()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add_photo_24_white),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.add_photo))
                            }
                        },
                        onClick = {
                            expanded = false
                            onMakePhotoClick()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add_video_24_white),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.add_video))
                            }
                        },
                        onClick = {
                            expanded = false
                            onMakeVideoClick()
                        },
                        enabled = false
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_delete_24_white),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.delete))
                            }
                        },
                        onClick = {
                            expanded = false
                            onDeleteClick()
                        }
                    )
                }
            }
        }
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

@Composable
fun CoachItemCard(
    coach: PassengerCar,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.OFF_WHITE.color,
            contentColor = AppColors.MAIN_GREEN.color
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                val coachNumber = listOfNotNull(
                    coach.number,
                    coach.coachType.passengerCoachTitle
                ).joinToString(", ")
                var coachInfo = ""
                if (coach.trailing == true) {
                    coachInfo = listOfNotNull(
                        stringResource(R.string.trailing_string),
                        coach.depotDomain.shortName,
                        coach.coachRoute
                    ).joinToString("\n")
                } else {
                    coachInfo = coach.depotDomain.shortName
                }

                Text(
                    coachNumber,
                    style = AppTypography.bodyLarge
                )

                Text(
                    coachInfo,
                    style = AppTypography.bodyMedium
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete_32_white),
                    contentDescription = stringResource(R.string.delete)
                )
            }
        }
    }
}

@Composable
fun CoachItemCardReadOnly(
    coach: PassengerCar,
    onItemClick: () -> Unit
) {
    val isChecked = coach.revisionDateEnd != null
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 1.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onItemClick),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.OFF_WHITE.color,
                contentColor = AppColors.MAIN_GREEN.color
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {

                    val coachNumber = listOfNotNull(
                        coach.number,
                        coach.coachType.passengerCoachTitle
                    ).joinToString(", ")

                    val coachInfo = if (coach.trailing == true) {
                        listOfNotNull(
                            stringResource(R.string.trailing_string),
                            coach.depotDomain.shortName,
                            coach.coachRoute
                        ).joinToString("\n")
                    } else {
                        coach.depotDomain.shortName
                    }

                    Text(
                        coachNumber,
                        style = AppTypography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        coachInfo,
                        style = AppTypography.bodyMedium
                    )
                }
            }
        }

        Badge(
            containerColor = if (isChecked) AppColors.DARK_GREEN.color else AppColors.MATERIAL_RED.color,
            contentColor = AppColors.OFF_WHITE.color,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-8).dp, y = 8.dp)
        ) {
            Text(if (isChecked) stringResource(R.string.checked) else stringResource(R.string.uncheked))
        }
    }
}

@Composable
fun InnerWorkerItemCard(worker: InnerWorkerDomain, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.OFF_WHITE.color,
            contentColor = AppColors.MAIN_GREEN.color
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    worker.name,
                    style = AppTypography.bodyLarge
                )
                val info = listOfNotNull(
                    worker.workerType?.description,
                    worker.depotDomain?.shortName
                ).joinToString(", ")
                if (info.isNotBlank()) {
                    Text(
                        info,
                        style = AppTypography.bodyMedium
                    )
                }
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete_32_white),
                    contentDescription = stringResource(R.string.delete)
                )
            }
        }
    }
}

@Composable
fun CoachDropDownItemCard(
    coach: Coach
) {
    var expanded by remember { mutableStateOf(false) }

    val depot = when (coach) {
        is PassengerCar -> coach.depotDomain.shortName
        is DinnerCar -> if (coach.companyDomain != null) {
            coach.companyDomain.name
        } else {
            coach.depot.shortName
        }

        else -> null
    }

    val workerDepot = when (coach) {
        is PassengerCar -> (coach.worker as? InnerWorkerDomain)?.depotDomain?.shortName
        is DinnerCar -> if (coach.companyDomain != null) {
            (coach.worker as? OuterWorkerDomain)?.company?.name
        } else {
            (coach.worker as? InnerWorkerDomain)?.depotDomain?.shortName
        }

        else -> null
    }

    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.OFF_WHITE.color
        )
    ) {
        Text(
            text = coach.number,
            style = AppTypography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                ServiceInfoBlock(
                    label = stringResource(R.string.coach_data),
                    content = {
                        Text(
                            text = "${stringResource(R.string.depot_string)}: $depot",
                            style = AppTypography.bodyMedium
                        )
                        Text(
                            text = "${stringResource(R.string.revision_start)}: ${
                                coach.revisionDateStart.format(
                                    formatter
                                )
                            }",
                            style = AppTypography.bodyMedium
                        )
                        Text(
                            text = "${stringResource(R.string.revision_end)}: ${
                                coach.revisionDateEnd.format(
                                    formatter
                                )
                            }",
                            style = AppTypography.bodyMedium
                        )
                        ServiceInfoBlock(
                            label = stringResource(R.string.worker_data),
                            content = {
                                Column(
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(
                                        text = "${stringResource(R.string.worker)}: ${coach.worker.name}",
                                        style = AppTypography.bodyMedium
                                    )
                                    Text(
                                        text = "${stringResource(R.string.worker_type)}: ${coach.worker.workerType.description}",
                                        style = AppTypography.bodyMedium
                                    )
                                    Text(
                                        text = "${stringResource(R.string.worker_id)}: ${coach.worker.id}",
                                        style = AppTypography.bodySmall
                                    )
                                    Text(
                                        text = "${stringResource(R.string.worker_depot)}: $workerDepot",
                                        style = AppTypography.bodyMedium
                                    )
                                }
                            }
                        )
                        ServiceInfoBlock(
                            label = stringResource(R.string.violations),
                            content = {
                                for (violation in coach.violationMap.values) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = violation.code.toString(),
                                            style = AppTypography.bodyMedium
                                        )
                                        Text(
                                            text = buildString {
                                                append(violation.name)
                                                if (violation.isResolved) {
                                                    append(" (${stringResource(R.string.resolved)})")
                                                }
                                                if (violation.attributeMap.isNotEmpty()) {
                                                    append(" [")
                                                    append(
                                                        violation.attributeMap.values.joinToString(
                                                            ", "
                                                        )
                                                    )
                                                    append(" ]")
                                                }
                                            },
                                            style = AppTypography.bodyMedium
                                        )
                                    }
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}