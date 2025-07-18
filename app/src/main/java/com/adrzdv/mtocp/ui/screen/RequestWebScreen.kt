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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.dialogs.CustomAlertDialog
import com.adrzdv.mtocp.ui.component.MenuButton
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.CustomTypography
import com.adrzdv.mtocp.ui.viewmodel.RequestWebViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestWebScreen(
    onBackClick: () -> Unit
) {
    val viewModel: RequestWebViewModel = viewModel()

    val isLoading = viewModel.isLoading
    val isGettingNumber = viewModel.isGettingNumber
    val showNameDialog = viewModel.showNameDialog
    val resultDialogText = viewModel.resultDialogText

    Scaffold(
        containerColor = AppColors.LIGHT_GRAY.color,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.web_request_string),
                        style = CustomTypography.titleLarge
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
                    containerColor = AppColors.MAIN_GREEN.color,
                    scrolledContainerColor = AppColors.MAIN_GREEN.color,
                    titleContentColor = AppColors.OFF_WHITE.color,
                    navigationIconContentColor = AppColors.OFF_WHITE.color,
                    actionIconContentColor = AppColors.OFF_WHITE.color
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

                MenuButton(
                    text = stringResource(R.string.request_string),
                    icon = painterResource(R.drawable.ic_webhook_pic_32_white),
                    onClick = { viewModel.requestRender() }
                )
            }

            if (isLoading || isGettingNumber) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = AppColors.MAIN_GREEN.color)
                }
            }

            if (showNameDialog) {
                CustomAlertDialog(viewModel, stringResource(R.string.new_worker))
            }

            resultDialogText?.let { text ->
                AlertDialog(
                    onDismissRequest = { viewModel.dismissDialogs() },
                    confirmButton = {
                        TextButton(onClick = { viewModel.dismissDialogs() }) {
                            Text(stringResource(R.string.ok_string))
                        }
                    },
                    title = { Text(stringResource(R.string.doc_number_string)) },
                    text = { Text(text) }
                )
            }
        }
    }
}