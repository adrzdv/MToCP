package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.ServiceInfoBlock
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.model.OrderViewModel

@Composable
fun ResultScreenStatsParam(
    orderViewModel: OrderViewModel
) {
    val coachParams = orderViewModel.statsParams
    val trainParams = orderViewModel.collector?.additionalParams

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.LIGHT_GRAY.color)
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Заголовок для блока main doors
        ServiceInfoBlock(
            label = "Automatic doors",
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // main auto doors
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.res_main_car),
                            style = AppTypography.bodyMedium,
                            modifier = Modifier.weight(1f),
                        )
                        Text(
                            text = orderViewModel.countMainAutodoors().toString(),
                            style = AppTypography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    HorizontalDivider(thickness = 1.dp, color = AppColors.LIGHT_GRAY.color)
                    // trailing auto doors
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                    ) {
                        Text(
                            text = stringResource(R.string.res_trailing_car),
                            style = AppTypography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = orderViewModel.countTrailingAutodoors().toString(),
                            style = AppTypography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        )

        // train additional parameters
        trainParams?.values?.let { params ->
            ServiceInfoBlock(
                label = stringResource(R.string.params_train),
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        params.forEachIndexed { index, value ->
                            val statusSuffix =
                                if (value.completed) " \n[ВЫПОЛНЕНО]" else " \n[НЕ ВЫПОЛНЕНО]"
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min),
                            ) {
                                Text(
                                    text = value.name,
                                    style = AppTypography.bodyMedium,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = value.note + statusSuffix,
                                    style = AppTypography.bodyMedium,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (index < params.size - 1) {
                                HorizontalDivider(
                                    thickness = 0.5.dp,
                                    color = AppColors.LIGHT_GRAY.color
                                )
                            }
                        }
                    }
                }
            )
        }
        // coaches additional parameters
        ServiceInfoBlock(
            label = stringResource(R.string.params_coach),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val keysList = coachParams.keys.toList()

                    for ((index, key) in keysList.withIndex()) {
                        ServiceInfoBlock(
                            label = key,
                            content = {
                                val innerMap = coachParams.getValue(key)
                                val innerKeysList = innerMap.keys.toList()

                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    for ((i, innerKey) in innerKeysList.withIndex()) {
                                        val pair = innerMap.getValue(innerKey)
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(IntrinsicSize.Min),
                                        ) {
                                            Text(
                                                text = innerKey,
                                                style = AppTypography.bodyMedium,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Text(
                                                text = pair.first,
                                                style = AppTypography.bodyMedium,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Text(
                                                text = pair.second,
                                                style = AppTypography.bodyMedium,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                        if (i < innerKeysList.lastIndex) {
                                            HorizontalDivider(
                                                thickness = 0.5.dp,
                                                color = AppColors.LIGHT_GRAY.color
                                            )
                                        }
                                    }
                                }
                            }
                        )
                        if (index < keysList.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp),
                                thickness = 1.dp,
                                color = AppColors.LIGHT_GRAY.color
                            )
                        }
                    }
                }
            }
        )
    }

}