package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.dialogs.CustomAlertDialog
import com.adrzdv.mtocp.ui.component.newelements.NothingToShowPlug
import com.adrzdv.mtocp.ui.component.newelements.SquaredBigButton
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.snackbar.ErrorSnackbar
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.CustomTypography
import com.adrzdv.mtocp.ui.viewmodel.RequestWebViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestWebScreen(
    username: String,
    onBackClick: () -> Unit
) {
    val viewModel: RequestWebViewModel = viewModel()
    val isLoading = viewModel.isLoading
    val isGettingNumber = viewModel.isGettingNumber
    val showNameDialog = viewModel.showNameDialog
    val resultDialogText = viewModel.resultDialogText
    val snackbarHostState = remember { SnackbarHostState() }
    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = AppColors.BACKGROUND_COLOR.color,
        snackbarHost = { CustomSnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.web_request_string),
                        style = CustomTypography.titleLarge,
                        color = AppColors.SURFACE_COLOR.color
                    )
                },
                actions = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_home_24_white),
                            contentDescription = stringResource(R.string.back_text)
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = AppColors.MAIN_COLOR.color,
                    scrolledContainerColor = AppColors.MAIN_COLOR.color,
                    navigationIconContentColor = AppColors.SURFACE_COLOR.color,
                    titleContentColor = AppColors.SURFACE_COLOR.color,
                    actionIconContentColor = AppColors.SURFACE_COLOR.color,
                    subtitleContentColor = AppColors.BACKGROUND_COLOR.color
                )
            )
        }
    ) { innerPadding ->

        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 12.dp),
            state = state,
            isRefreshing = isRefreshing,
            onRefresh = {
                scope.launch {
                    isRefreshing = true
                    viewModel.loadLastLogs()
                    isRefreshing = false
                }
            },
            indicator = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ){
                    PullToRefreshDefaults.Indicator(
                        state = state,
                        isRefreshing = isRefreshing,
                        containerColor = AppColors.SURFACE_COLOR.color,
                        color = AppColors.MAIN_COLOR.color
                    )
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        SquaredBigButton(
                            text = stringResource(R.string.request_string),
                            icon = painterResource(R.drawable.ic_webhook_pic_32_white),
                            onClick = {
                                if (username.isNotEmpty() && username != "Unknown") {
                                    viewModel.setWorkerIfTokenExist(username)
                                }
                                viewModel.requestRender()
                            }
                        )
                    }

                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {

                            Text(
                                text = stringResource(R.string.number_symbol),
                                modifier = Modifier.weight(1f),
                                style = CustomTypography.titleSmall,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = stringResource(R.string.worker_table_header),
                                modifier = Modifier.weight(3f),
                                style = CustomTypography.titleSmall,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = stringResource(R.string.date_table_header),
                                modifier = Modifier.weight(3f),
                                style = CustomTypography.titleSmall,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                if (viewModel.isLogsLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = AppColors.MAIN_COLOR.color
                            )
                        }
                    }
                } else if (viewModel.logs.isEmpty()) {
                    item {
                        NothingToShowPlug()
                    }
                } else {
                    items(viewModel.logs) { log ->
                        val formattedDate = log.timestamp
                            .replace("T", " ")
                            .substring(0, 16)
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Text(
                                    text = log.number.toString(),
                                    modifier = Modifier.weight(1f),
                                    style = CustomTypography.bodyMedium,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = log.worker,
                                    modifier = Modifier.weight(3f),
                                    style = CustomTypography.bodyMedium,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = formattedDate,
                                    modifier = Modifier.weight(3f),
                                    style = CustomTypography.bodyMedium,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            if (isLoading || isGettingNumber) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = AppColors.MAIN_COLOR.color)
                }
            }

            if (showNameDialog && username.isEmpty() || username == "Unknown") {
                CustomAlertDialog(viewModel, stringResource(R.string.new_worker))
            }

            LaunchedEffect(resultDialogText) {
                viewModel.loadLastLogs()
                resultDialogText?.let { text ->
                    snackbarHostState.showSnackbar(visuals = ErrorSnackbar(message = text))
                }
                viewModel.dismissDialogs()
            }
        }
    }
}