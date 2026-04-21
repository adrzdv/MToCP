package com.adrzdv.mtocp.ui.component.newelements.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.ui.model.dto.CoachUIBase
import com.adrzdv.mtocp.ui.model.dto.CoachUi
import com.adrzdv.mtocp.ui.model.dto.DinnerCarUI
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

/**
 * A composable card element that displays information about a [PassengerCar].
 *
 * This card shows the coach number, type, depot information, and route. It includes a
 * status badge indicating whether the coach has been checked (based on the presence
 * of a revision end date) and a dropdown menu for actions.
 *
 * @param coach The [PassengerCar] object containing the data to be displayed.
 * @param onCoachClick An optional callback invoked when the "Open" action is selected from the menu.
 * If null, the status badge and "Open" menu option will not be displayed.
 * @param onDeleteClick A callback invoked when the "Delete" action is selected from the menu.
 */
@Composable
fun CoachCard(
    coach: CoachUIBase,
    onCoachClick: (() -> Unit)? = null,
    onDeleteClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val isChecked = coach.revisionEnd != null
    val coachNumber = listOfNotNull(
        coach.number,
        coach.type
    ).joinToString(", ")
    var coachInfo = ""

    if (coach.globalType == CoachTypes.PASSENGER_CAR) {
        (coach as CoachUi).let {
            coachInfo = if (coach.isTrailing) {
                listOfNotNull(
                    stringResource(R.string.trailing_string),
                    coach.depot,
                    coach.route
                ).joinToString("\n")
            } else {
                coach.depot
            }
        }
    } else {
        (coach as DinnerCarUI).let {
            coachInfo = coach.depot.ifEmpty { coach.company }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = AppColors.MAIN_COLOR.color,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 6.dp, vertical = 1.dp)
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
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
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

                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                painterResource(R.drawable.ic_more_vert),
                                tint = AppColors.MAIN_COLOR.color,
                                contentDescription = "Actions"
                            )
                        }
                        DropdownMenu(
                            modifier = Modifier.background(color = AppColors.SURFACE_COLOR.color),
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            onCoachClick?.let {
                                MenuTextElement(
                                    text = stringResource(R.string.open),
                                    onClick = onCoachClick,
                                    onDismiss = { expanded = false }
                                )
                            }
                            MenuTextElement(
                                text = stringResource(R.string.delete),
                                onClick = onDeleteClick,
                                onDismiss = { expanded = false }
                            )
                        }
                    }
                }

            }
        }
        onCoachClick?.let {
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
}

@Composable
private fun MenuTextElement(
    text: String? = null,
    onClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    DropdownMenuItem(
        text = {
            Text(
                text = text ?: "",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = AppColors.MAIN_COLOR.color
            )
        },
        onClick = {
            onClick?.invoke()
            onDismiss?.invoke()
        }
    )
}