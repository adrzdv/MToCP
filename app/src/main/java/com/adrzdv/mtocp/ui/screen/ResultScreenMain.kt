package com.adrzdv.mtocp.ui.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.buttons.MediumMenuButton
import com.adrzdv.mtocp.ui.component.ServiceInfoBlock
import com.adrzdv.mtocp.ui.intent.ShareIntentBuilder
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun ResultScreenMain(
    orderViewModel: OrderViewModel,
    snackbarHostState: SnackbarHostState
) {
    val smsReport by orderViewModel.smsReport.observeAsState("")
    val context = LocalContext.current
    val reportText = stringResource(R.string.report_share)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        orderViewModel.generateReport()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.LIGHT_GRAY.color)
            .padding(vertical = 8.dp)
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ServiceInfoBlock(
            label = stringResource(R.string.object_data),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    ResultScreenRow(content = {
                        Text(
                            text = stringResource(R.string.order_text),
                            style = AppTypography.bodyMedium
                        )
                        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                        Text(
                            text = orderViewModel.orderNumber + " от " + orderViewModel.dateStart.format(
                                formatter
                            ),
                            style = AppTypography.bodyMedium
                        )
                    })
                    ResultScreenRow(content = {
                        Text(
                            text = stringResource(R.string.object_number),
                            style = AppTypography.bodyMedium
                        )
                        Text(
                            text = orderViewModel.objectNumber,
                            style = AppTypography.bodyMedium
                        )
                    })
                    ResultScreenRow(content = {
                        Text(
                            text = stringResource(R.string.order_route_hint),
                            style = AppTypography.bodyMedium
                        )
                        Text(text = orderViewModel.route, style = AppTypography.bodyMedium)
                    })
                    ResultScreenRow(content = {
                        Text(
                            text = stringResource(R.string.res_violations_count),
                            style = AppTypography.bodyMedium
                        )
                        Text(
                            text = orderViewModel.totalViolation.toString(),
                            style = AppTypography.bodyMedium
                        )
                    })
                }
            }
        )
        //Crew block
        ServiceInfoBlock(
            label = stringResource(R.string.crew),
            content = {
                for (worker in orderViewModel.crewMap) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = worker.key,
                            style = AppTypography.bodyMedium,
                            modifier = Modifier.weight(0.35f)
                        )
                        Text(
                            text = worker.value.name,
                            style = AppTypography.bodyMedium,
                            modifier = Modifier.weight(0.5f)
                        )
                        Text(
                            text = worker.value.id.toString(),
                            style = AppTypography.bodyMedium,
                            modifier = Modifier.weight(0.15f)
                        )
                    }
                }
            }
        )
        //Main part of train block
        ServiceInfoBlock(
            label = stringResource(R.string.res_main_car)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.res_stat_coaches),
                    style = AppTypography.bodyMedium
                )
                Text(
                    text = orderViewModel.mainCars.toString(),
                    style = AppTypography.bodyMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.violations),
                    style = AppTypography.bodyMedium
                )
                Text(
                    text = orderViewModel.mainViolationCount.toString(),
                    style = AppTypography.bodyMedium
                )
            }
        }
        //Trailing cars stats block
        ServiceInfoBlock(
            label = stringResource(R.string.res_trailing_car)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.res_stat_coaches),
                    style = AppTypography.bodyMedium
                )
                Text(
                    text = orderViewModel.trailingCars.toString(),
                    style = AppTypography.bodyMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.violations),
                    style = AppTypography.bodyMedium
                )
                Text(
                    text = orderViewModel.trailingViolationCount.toString(),
                    style = AppTypography.bodyMedium
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MediumMenuButton(
                    onClick = {
                        orderViewModel.makeArchive { file ->
                            if (file == null || !file.exists()) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(MessageCodes.FILE_ERROR.messageTitle)
                                }
                                return@makeArchive
                            }
                            val intent = ShareIntentBuilder.shareZip(context, file)
                            context.startActivity(
                                Intent.createChooser(
                                    intent,
                                    "Share zip"
                                )
                            )
                        }
                    },
                    isEnable = true,
                    text = stringResource(R.string.zip_data),
                    icon = {
                        painterResource(R.drawable.ic_zip_24_white)
                    },
                    color = null
                )
                MediumMenuButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, smsReport)
                        }
                        context.startActivity(
                            Intent.createChooser(
                                intent,
                                reportText
                            )
                        )
                    },
                    isEnable = true,
                    icon = { painterResource(R.drawable.ic_report_24_white) },
                    text = stringResource(R.string.report),
                    color = null
                )
            }
        }
    }
}

@Composable
fun ResultScreenRow(
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

