package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.ConfirmDialog
import com.adrzdv.mtocp.ui.component.MenuButton
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartMenuScreen(
    onStartRevisionClick: () -> Unit,
    onOpenViolationCatalogClick: () -> Unit,
    onServiceMenuClick: () -> Unit,
    onExitClick: () -> Unit,
    onHelpClick: () -> Unit,
    appVersion: String
) {
    var showExitDialog by remember { mutableStateOf(false) }

    Scaffold(
        contentColor = AppColors.LIGHT_GRAY.color,
        topBar = {
            TopAppBar(
                title = {
                    Spacer(modifier = Modifier.height(0.dp))
                },
                colors = TopAppBarColors(
                    containerColor = AppColors.MAIN_GREEN.color,
                    scrolledContainerColor = AppColors.MAIN_GREEN.color,
                    titleContentColor = AppColors.MAIN_GREEN.color,
                    navigationIconContentColor = AppColors.MAIN_GREEN.color,
                    actionIconContentColor = AppColors.MAIN_GREEN.color
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
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.main_menu_text),
                    style = AppTypography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp),
                    color = AppColors.MAIN_GREEN.color
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    MenuButton(
                        text = stringResource(R.string.start_revision_text),
                        icon = painterResource(R.drawable.ic_start_revision_32_white),
                        onClick = onStartRevisionClick
                    )
                    MenuButton(
                        text = stringResource(R.string.violation_catalog_string),
                        icon = painterResource(R.drawable.ic_list_24_white),
                        onClick = onOpenViolationCatalogClick
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    MenuButton(
                        text = stringResource(R.string.service_menu_string),
                        icon = painterResource(R.drawable.ic_tool_48_white),
                        onClick = onServiceMenuClick
                    )
                    MenuButton(
                        text = stringResource(R.string.exit_text),
                        icon = painterResource(R.drawable.ic_exit_24_white),
                        onClick = {
                            showExitDialog = true
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    MenuButton(
                        text = stringResource(R.string.help),
                        icon = painterResource(R.drawable.ic_help_24_white),
                        onClick = onHelpClick
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }

            Text(
                text = appVersion,
                fontSize = 12.sp,
                color = Color.DarkGray,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            if (showExitDialog) {
                ConfirmDialog(
                    title = stringResource(R.string.exit_text),
                    message = "Вы уверены, что хотите выйти?",
                    onConfirm = {
                        showExitDialog = false
                        onExitClick()
                    },
                    onDismiss = { showExitDialog = false }
                )
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    StartMenuScreen(
        onStartRevisionClick = {},
        onOpenViolationCatalogClick = {},
        onServiceMenuClick = {},
        onExitClick = {},
        onHelpClick = {},
        appVersion = "v1.0.0"
    )
}