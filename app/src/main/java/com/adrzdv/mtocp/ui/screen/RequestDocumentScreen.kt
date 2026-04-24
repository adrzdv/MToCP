package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.dialogs.CustomAlertDialog
import com.adrzdv.mtocp.ui.component.newelements.NothingToShowPlug
import com.adrzdv.mtocp.ui.component.newelements.SquaredBigButton
import com.adrzdv.mtocp.ui.component.newelements.cards.DocumentCard
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.CustomTypography
import com.adrzdv.mtocp.ui.viewmodel.model.RequestDocumentViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDocumentScreen(
    username: String,
    prefix: String? = null,
    requestDocumentViewModel: RequestDocumentViewModel,
    onBackClick: () -> Unit
) {

    val listState = rememberLazyListState()

    val isLoading = requestDocumentViewModel.isLoading
    val isGettingNumber = requestDocumentViewModel.isGettingNumber
    val showNameDialog = requestDocumentViewModel.showNameDialog
    val resultDialogText = requestDocumentViewModel.resultDialogText

    val snackbarHostState = remember { SnackbarHostState() }

    var isRefreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullToRefreshState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (!prefix.isNullOrEmpty()) {
            requestDocumentViewModel.setWorkerPrefixIfExist(prefix)
        }
        requestDocumentViewModel.loadFirstPage()
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { index ->
            if (index != null && index >= requestDocumentViewModel.logs.size - 3) {
                requestDocumentViewModel.loadNextPage()
            }
        }
    }

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
                .padding(innerPadding),
            state = refreshState,
            isRefreshing = isRefreshing,
            onRefresh = {
                scope.launch {
                    isRefreshing = true
                    requestDocumentViewModel.loadFirstPage()
                    isRefreshing = false
                }
            }
        ) {

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        SquaredBigButton(
                            text = stringResource(R.string.request_string),
                            icon = painterResource(R.drawable.ic_webhook_pic_32_white),
                            onClick = {
                                if (username.isNotEmpty() && username != "Unknown") {
                                    requestDocumentViewModel.setWorkerIfTokenExist(username)
                                }
                                if (!prefix.isNullOrEmpty()) {
                                    requestDocumentViewModel.setWorkerPrefixIfExist(prefix)
                                }
                                requestDocumentViewModel.requestList()
                            }
                        )
                    }
                }

                items(requestDocumentViewModel.logs) { log ->
                    DocumentCard(log)
                }

                if (requestDocumentViewModel.isLogsLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                if (!requestDocumentViewModel.isLogsLoading && requestDocumentViewModel.logs.isEmpty()) {
                    item {
                        NothingToShowPlug()
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
                    CircularProgressIndicator()
                }
            }

            if (showNameDialog && (username.isEmpty() || username == "Unknown")) {
                CustomAlertDialog(requestDocumentViewModel, stringResource(R.string.new_worker))
            }

            LaunchedEffect(resultDialogText) {
                resultDialogText?.let {
                    snackbarHostState.showSnackbar(it)
                    requestDocumentViewModel.dismissDialogs()
                }
            }
        }
    }
}