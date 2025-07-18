package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

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