package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.buttons.MenuButton
import com.adrzdv.mtocp.ui.component.dialogs.CustomAlertDialog
import com.adrzdv.mtocp.ui.component.newelements.SquaredBigButton
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.snackbar.ErrorSnackbar
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.CustomTypography
import com.adrzdv.mtocp.ui.viewmodel.RequestWebViewModel

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

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
                resultDialogText?.let { text ->
                    snackbarHostState.showSnackbar(visuals = ErrorSnackbar(message = text))
                }
                viewModel.dismissDialogs()
            }
        }
    }
}