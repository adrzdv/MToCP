package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.AddWorkerDialog
import com.adrzdv.mtocp.ui.component.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.InnerWorkerItemCard
import com.adrzdv.mtocp.ui.component.MediumMenuButton
import com.adrzdv.mtocp.ui.theme.CustomTypography
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.InnerWorkerViewModel
import kotlinx.coroutines.launch

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddCrewScreen(
//    orderViewModel: OrderViewModel,
//    navController: NavController,
//    depotViewModel: DepotViewModel,
//    onBackPress: () -> Unit,
//    viewModel: InnerWorkerViewModel = viewModel()
//) {
//
//    val snackbarHostState = remember { SnackbarHostState() }
//    val workers = viewModel.workers
//    var showDialog by remember { mutableStateOf(false) }
//    var selectedDepotString = ""
//    var checked by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope()
//
//    fun saveOrderArgs() {
//
//    }
//
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    saveOrderArgs()
//                    //navController.navigate("addCoaches")
//                },
//                containerColor = Color(0xFF4CAF50),
//                contentColor = Color.White
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_save_48_white),
//                    contentDescription = stringResource(R.string.save_string)
//                )
//            }
//        },
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = stringResource(R.string.masters_object),
//                        style = CustomTypography.titleLarge
//                    )
//                },
//                navigationIcon = {
//                    IconButton(
//                        onClick = {
//                            onBackPress()
//                        }
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_back_32),
//                            contentDescription = stringResource(R.string.back_text)
//                        )
//
//                    }
//                }
//            )
//
//        },
//        snackbarHost = {
//            CustomSnackbarHost(
//                hostState = snackbarHostState
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                MenuButton(
//                    text = "Добавить работника",
//                    icon = painterResource(R.drawable.ic_add_person),
//                    onClick = { showDialog = true }
//                )
//
//                MenuButton(
//                    text = "Очистить список",
//                    icon = painterResource(R.drawable.ic_clear_list),
//                    onClick = { viewModel.cleanWorkers() }
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Checkbox(
//                    checked = checked,
//                    onCheckedChange = { checked = it }
//                )
//                Text(
//                    text = "QUALITY PASSPORT",
//                    modifier = Modifier.padding(start = 8.dp)
//                )
//
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                modifier = Modifier.fillMaxSize()
//            ) {
//                items(workers) { worker ->
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 8.dp),
//                        shape = RoundedCornerShape(12.dp),
//                        elevation = CardDefaults.cardElevation(4.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .padding(end = 8.dp)
//                            ) {
//                                Text(
//                                    text = worker.name,
//                                    style = MaterialTheme.typography.titleMedium
//                                )
//                                Spacer(modifier = Modifier.height(4.dp))
//                                Text(
//                                    text = listOfNotNull(
//                                        worker.workerType?.description,
//                                        worker.depotDomain?.name
//                                    ).joinToString(", "),
//                                    style = MaterialTheme.typography.bodyMedium,
//                                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                                )
//                            }
//                            IconButton(onClick = { viewModel.removeWorker(worker) }) {
//                                Icon(
//                                    imageVector = Icons.Default.Delete,
//                                    contentDescription = "Удалить работника"
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//
//
//        }
//
//    }
//    if (showDialog) {
//        AddWorkerDialog(
//            orderViewModel,
//            depotViewModel,
//            onDismiss = { showDialog = false }
//        )
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCrewScreen(
    orderViewModel: OrderViewModel,
    navController: NavController,
    depotViewModel: DepotViewModel,
    onBackPress: () -> Unit,
    viewModel: InnerWorkerViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val workers = viewModel.workers
    var showDialog by remember { mutableStateOf(false) }
    var checkedQualityPassport by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.masters_object),
                        style = CustomTypography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPress() }) {
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
                containerColor = Color(0xFF4CAF50),
                onClick = {
                    //navController.navigate("addCoaches")
                    orderViewModel.addQualityPassport(checkedQualityPassport)
                    if (orderViewModel.checkCrew()) {

                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = MessageCodes.CREW_ERROR.errorTitle
                            )
                        }
                    }
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
                        style = CustomTypography.labelLarge
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
                            painter = painterResource(R.drawable.ic_add_person),
                            contentDescription = null
                        )
                    },
                    stringResource(R.string.add_string)
                )

                MediumMenuButton(
                    onClick = {
                        viewModel.cleanWorkers()
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Checkbox(
                    checked = checkedQualityPassport,
                    onCheckedChange = { checkedQualityPassport = it }
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("QUALITY PASSPORT", style = MaterialTheme.typography.bodyMedium)
            }

            HorizontalDivider()

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(workers) { worker ->
                    InnerWorkerItemCard(worker = worker) {
                        viewModel.removeWorker(worker)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddWorkerDialog(
            orderViewModel,
            depotViewModel,
            onDismiss = { showDialog = false }
        )
    }
}