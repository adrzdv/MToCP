package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.buttons.newelements.SquaredBigButton
import com.adrzdv.mtocp.ui.component.dialogs.ConfirmDialog
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
    onRequestWebClick: () -> Unit,
    appVersion: String
) {
    var showExitDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = AppColors.BACKGROUND_COLOR.color,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.main_menu_text),
                        style = AppTypography.titleLarge,
                        color = AppColors.SURFACE_COLOR.color
                    )
                    Spacer(modifier = Modifier.height(0.dp))
                },
                actions = {
                    IconButton(
                        onClick = onServiceMenuClick
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_outline_settings_24),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = AppColors.MAIN_COLOR.color,
                    scrolledContainerColor = AppColors.SECONDARY_COLOR.color,
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
                .padding(bottom = 12.dp)
                .background(color = AppColors.BACKGROUND_COLOR.color)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 64.dp,
                        bottomEnd = 64.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.MAIN_COLOR.color
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.app_logo),
                                contentDescription = null,
                                alignment = Alignment.Center,
                                contentScale = ContentScale.Inside,
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Surname Name Secondname",
                                    style = AppTypography.titleLarge,
                                    color = AppColors.SURFACE_COLOR.color
                                )
                                Text(
                                    text = "ID: 70000000",
                                    style = AppTypography.titleMedium,
                                    color = AppColors.SURFACE_COLOR.color
                                )
                                Text(
                                    text = "ID: 000038",
                                    style = AppTypography.titleMedium,
                                    color = AppColors.SURFACE_COLOR.color
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        SquaredBigButton(
                            text = stringResource(R.string.start_revision_text),
                            icon = painterResource(R.drawable.ic_start_revision_32_white),
                            onClick = onStartRevisionClick
                        )
                        SquaredBigButton(
                            text = stringResource(R.string.violation_catalog_string),
                            icon = painterResource(R.drawable.ic_list_24_white),
                            onClick = onOpenViolationCatalogClick
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        SquaredBigButton(
                            text = stringResource(R.string.web_request_string),
                            icon = painterResource(R.drawable.ic_outline_globe_24),
                            onClick = onRequestWebClick
                        )

                        SquaredBigButton(
                            text = stringResource(R.string.help),
                            icon = painterResource(R.drawable.ic_help_24_white),
                            onClick = {}
                        )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .weight(0.2f)
                )
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
                    message = stringResource(R.string.ask_exit_string),
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
        onRequestWebClick = {},
        appVersion = "v1.0.0"
    )
}