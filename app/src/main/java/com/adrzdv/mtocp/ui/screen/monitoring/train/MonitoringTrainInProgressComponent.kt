package com.adrzdv.mtocp.ui.screen.monitoring.train

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.newelements.BlancInfoBlock
import com.adrzdv.mtocp.ui.component.newelements.SquaredMediumButton
import com.adrzdv.mtocp.ui.component.newelements.cards.CoachCard
import com.adrzdv.mtocp.ui.model.dto.TrainUI
import com.adrzdv.mtocp.ui.state.order.TrainOrderState
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.viewmodel.model.TrainOrderViewModel

/**
 * Показывает экран мониторинга поезда.
 */
@Composable
fun MonitoringTrainInProgressContent(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    state: TrainOrderState,
    trainOrderViewModel: TrainOrderViewModel
) {
    var showEditCoachDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            TrainDataBlock(
                modifier = modifier,
                train = state.train
            )
        }
        item {
            BlancInfoBlock(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.train_scheme_text) + "\n" +
                            stringResource(R.string.total) + state.coachList.size + " \n" +
                            state.trainScheme,
                    color = AppColors.MAIN_COLOR.color,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        item {
            ButtonsCoachBlock(
                modifier,
                onCoachAddClick = { },
                onClearCoachListClick = { trainOrderViewModel.clearCoaches() },
                onOtherParamsClick = { },
                onInfoClick = {}
            )
        }
        items(state.coachList.values.toList()) {
            CoachCard(
                coach = it,
                onCoachClick = {},
                onDeleteClick = { trainOrderViewModel.removeCoachInOrder(it.id) }
            )
        }
    }

    if (showEditCoachDialog) {

    }
}

@Composable
fun TrainDataBlock(
    modifier: Modifier = Modifier,
    train: TrainUI
) {
    BlancInfoBlock {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TrainNumberBlock(
                modifier = modifier,
                train = train
            )
            HorizontalDivider(thickness = 4.dp, color = AppColors.SURFACE_COLOR.color)
            TrainAdditionalDataBlock(
                modifier = modifier,
                train = train
            )
        }
    }
}

@Composable
fun TrainNumberBlock(
    modifier: Modifier,
    train: TrainUI
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            painter = painterResource(R.drawable.ic_outline_train_24),
            contentDescription = stringResource(R.string.trains),
            tint = AppColors.MAIN_COLOR.color
        )
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = train.number + " " + train.route,
                color = AppColors.MAIN_COLOR.color
            )
            Text(
                text = train.depot + ", " + train.branch,
                color = AppColors.MAIN_COLOR.color
            )
        }
    }
}

@Composable
fun TrainAdditionalDataBlock(
    modifier: Modifier,
    train: TrainUI
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_progressive_service),
                contentDescription = stringResource(R.string.progress_using),
                tint = if (train.isProgressive
                        ?: false
                ) AppColors.MAIN_COLOR.color else Color.Gray.copy(
                    alpha = 0.5f
                )
            )
            Text(
                text = stringResource(R.string.progress_using),
                textAlign = TextAlign.Center,
                color = if (train.isProgressive
                        ?: false
                ) AppColors.MAIN_COLOR.color else Color.Gray.copy(
                    alpha = 0.5f
                )
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_cctv),
                contentDescription = stringResource(R.string.cctv_using),
                tint = if (train.isCCTV ?: false) AppColors.MAIN_COLOR.color else Color.Gray.copy(
                    alpha = 0.5f
                )
            )
            Text(
                text = stringResource(R.string.cctv_using),
                textAlign = TextAlign.Center,
                color = if (train.isCCTV ?: false) AppColors.MAIN_COLOR.color else Color.Gray.copy(
                    alpha = 0.5f
                )
            )
        }
    }
}

@Composable
fun ButtonsCoachBlock(
    modifier: Modifier,
    onCoachAddClick: () -> Unit,
    onClearCoachListClick: () -> Unit,
    onOtherParamsClick: () -> Unit,
    onInfoClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth(),
            userScrollEnabled = false
        ) {
            item {
                SquaredMediumButton(
                    onClick = {},
                    text = stringResource(R.string.new_coach),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_add_item),
                            contentDescription = stringResource(R.string.new_coach)
                        )
                    }
                )
            }
            item {
                SquaredMediumButton(
                    onClick = {
                        onClearCoachListClick()
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_clear_list),
                            contentDescription = stringResource(R.string.new_coach)
                        )
                    },
                    text = stringResource(R.string.clear_text)
                )
            }
            item {
                SquaredMediumButton(
                    onClick = {},
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_add_tag_24_white),
                            contentDescription = stringResource(R.string.new_coach)
                        )
                    },
                    text = stringResource(R.string.order_other_params)
                )
            }
            item {
                SquaredMediumButton(
                    onClick = {},
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_information_24_white),
                            contentDescription = stringResource(R.string.info)
                        )
                    },
                    text = stringResource(R.string.info)
                )
            }
        }
    }
}