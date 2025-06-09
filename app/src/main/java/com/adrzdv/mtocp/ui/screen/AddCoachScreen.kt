package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.AddCoachDialog
import com.adrzdv.mtocp.ui.component.AddWorkerDialog
import com.adrzdv.mtocp.ui.component.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.InnerWorkerItemCard
import com.adrzdv.mtocp.ui.component.MediumMenuButton
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCoachScreen(
    orderViewModel: OrderViewModel,
    navController: NavController,
    depotViewModel: DepotViewModel,
    onBackPressed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.masters_object),
                        style = AppTypography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_32),
                            contentDescription = stringResource(R.string.back_text)
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            ExtendedFloatingActionButton(
                contentColor = Color.White,
                containerColor = AppColors.MAIN_GREEN.color,
                onClick = {

                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_save_32_white),
                        contentDescription = stringResource(R.string.save_string)
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.save_string),
                        style = AppTypography.labelLarge
                    )
                }
            )
        },
        snackbarHost = {
            CustomSnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                MediumMenuButton(
                    onClick = { showDialog = true },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_add_itew_white),
                            contentDescription = null
                        )
                    },
                    stringResource(R.string.add_string)
                )

                MediumMenuButton(
                    onClick = {

                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_clear_list),
                            contentDescription = null
                        )
                    },
                    stringResource(R.string.clean_string)
                )
            }

            HorizontalDivider()

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxSize()
            ) {

            }
        }
    }

    if (showDialog) {
        AddCoachDialog(
            orderViewModel,
            depotViewModel,
            onDismiss = {
                showDialog = false
            }
        )
    }
}