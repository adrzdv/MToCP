package com.adrzdv.mtocp.ui.component.newelements.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.Coach
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain
import com.adrzdv.mtocp.domain.model.workers.OuterWorkerDomain
import com.adrzdv.mtocp.ui.component.ServiceInfoBlock
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import java.time.format.DateTimeFormatter

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
            containerColor = AppColors.SURFACE_COLOR.color,
            contentColor = AppColors.MAIN_COLOR.color
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
                containerColor = AppColors.SURFACE_COLOR.color,
                contentColor = AppColors.MAIN_COLOR.color
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
            containerColor = if (isChecked) AppColors.DARK_GREEN.color else AppColors.ERROR_COLOR.color,
            contentColor = AppColors.SURFACE_COLOR.color,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-8).dp, y = 8.dp)
        ) {
            Text(if (isChecked) stringResource(R.string.checked) else stringResource(R.string.uncheked))
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
            containerColor = AppColors.SURFACE_COLOR.color
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