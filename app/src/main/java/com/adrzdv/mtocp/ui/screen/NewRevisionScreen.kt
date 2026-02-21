package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.newelements.SquaredBigButton
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRevisionScreen(
    navController: NavHostController? = null,
    onBackClick: () -> Unit,
    onTrainRevisionClick: () -> Unit,
    onTicketOfficeRevisionClick: () -> Unit,
    onCoachRevisionClick: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = AppColors.SURFACE_COLOR.color,
        topBar = {
            TopAppBar(
                title = {
                    Spacer(modifier = Modifier.height(0.dp))
                    Text(
                        text = stringResource(id = R.string.start_revision_text),
                        style = AppTypography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back_32_white),
                            contentDescription = stringResource(R.string.menu_string)
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = AppColors.MAIN_COLOR.color,
                    scrolledContainerColor = AppColors.MAIN_COLOR.color,
                    navigationIconContentColor = AppColors.SURFACE_COLOR.color,
                    titleContentColor = AppColors.SURFACE_COLOR.color,
                    actionIconContentColor = AppColors.SURFACE_COLOR.color,
                    subtitleContentColor = AppColors.SURFACE_COLOR.color
                )
            )
        },
        snackbarHost = { CustomSnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SquaredBigButton(
                icon = painterResource(R.drawable.ic_outline_train_24),
                text = stringResource(R.string.train_revision),
                onClick = {
                    onTrainRevisionClick()
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            SquaredBigButton(
                icon = painterResource(R.drawable.ic_wagon),
                text = stringResource(R.string.ticket_office_revision),
                onClick = {
                    onTicketOfficeRevisionClick()
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            SquaredBigButton(
                icon = painterResource(R.drawable.ic_wagon),
                text = stringResource(R.string.coach_revision),
                onClick = {
                    onCoachRevisionClick()
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewNewRevisionScreen() {
    NewRevisionScreen(
        onBackClick = {},
        onTrainRevisionClick = {},
        onTicketOfficeRevisionClick = {},
        onCoachRevisionClick = {}
    )
}
