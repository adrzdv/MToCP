package com.adrzdv.mtocp.ui.screen.monitoring.train

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import com.adrzdv.mtocp.ui.component.AppBar
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.viewmodel.model.TrainOrderViewModel

@Composable
fun MonitoringTrainInProgress(
    navController: NavHostController,
    appCacheRepository: CacheRepository,
    trainOrderViewModel: TrainOrderViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by trainOrderViewModel.orderState.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.revision_string),
                actions = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_save_32_white),
                            contentDescription = stringResource(R.string.save_string)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back_32_white),
                            contentDescription = stringResource(R.string.back_text)
                        )
                    }
                }
            )
        },
        snackbarHost = { CustomSnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        MonitoringTrainInProgressContent(
            innerPadding,
            state = state,
            trainOrderViewModel = trainOrderViewModel
        )
    }
}