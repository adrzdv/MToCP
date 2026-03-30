package com.adrzdv.mtocp.ui.screen.monitoring.train

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import com.adrzdv.mtocp.ui.component.AppBar
import com.adrzdv.mtocp.ui.component.BackNavigationButton
import com.adrzdv.mtocp.ui.component.SaveActionButton
import com.adrzdv.mtocp.ui.component.newelements.LabeledBlock
import com.adrzdv.mtocp.ui.component.newelements.SquaredMediumButton
import com.adrzdv.mtocp.ui.component.newelements.SquaredMediumButtonUnderneathText
import com.adrzdv.mtocp.ui.component.newelements.cards.ViolationCard
import com.adrzdv.mtocp.ui.model.dto.CoachUIBase
import com.adrzdv.mtocp.ui.screen.monitoring.train.sheet.EditCoachDataSheetContent
import com.adrzdv.mtocp.ui.screen.monitoring.train.sheet.EditCoachWorkerSheetContent
import com.adrzdv.mtocp.ui.state.coach.CoachDraftState
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.EditCoachViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCoachOnMonitoringScreen(
    navController: NavHostController,
    editCoachViewModel: EditCoachViewModel,
    appCacheRepository: CacheRepository,
    depotAutoCompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    workerAutoCompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    companyAutoCompleteViewModel: AutocompleteViewModel<CompanyWithBranch>,
    onSaveClick: (CoachUIBase) -> Unit
) {
    val state by editCoachViewModel.state.collectAsState()
    val isEditCoachDataSheetOpen by editCoachViewModel.isSheetCoachEditOpen.collectAsState()
    val isEditWorkerDataSheetOpen by editCoachViewModel.isSheetWorkerEditOpen.collectAsState()
    val editCoachSheetState = rememberModalBottomSheetState()

    Scaffold(
        containerColor = AppColors.SURFACE_COLOR.color,
        modifier = Modifier,
        topBar = {
            AppBar(
                title = stringResource(R.string.train_revision),
                actions = {
                    SaveActionButton(onClick = {

                    })
                },
                navigationIcon = {
                    BackNavigationButton(onClick = {
                        navController.popBackStack()
                    })
                }
            )
        }

    ) { innerPadding ->

        EditCoachOnMonitoringScreenContent(
            innerPadding,
            { editCoachViewModel.openCoachSheet() },
            { editCoachViewModel.openWorkerSheet() },
            state
        )

        if (isEditCoachDataSheetOpen || isEditWorkerDataSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = { editCoachViewModel.closeAnySheet() },
                sheetState = editCoachSheetState,
                containerColor = AppColors.SURFACE_COLOR.color,
            ) {
                if (isEditCoachDataSheetOpen) {
                    EditCoachDataSheetContent(
                        editCoachViewModel,
                        depotAutoCompleteViewModel,
                        companyAutoCompleteViewModel
                    ) { editCoachViewModel.closeAnySheet() }
                } else {
                    EditCoachWorkerSheetContent(
                        editCoachViewModel,
                        depotAutoCompleteViewModel
                    ) {
                        editCoachViewModel.onWorkerDataChange(it)
                        if (state.workerError == null) {
                            editCoachViewModel.closeAnySheet()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EditCoachOnMonitoringScreenContent(
    innerPadding: PaddingValues,
    onEditCoachDataClick: () -> Unit,
    onEditWorkerDataClick: () -> Unit,
    state: CoachDraftState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CoachInfoBlock(
                    modifier = Modifier.weight(1f),
                    state = state,
                    onClickButton = {
                        onEditCoachDataClick()
                    }
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                WorkerInfoBlock(
                    modifier = Modifier.weight(1f),
                    state = state,
                    onClickButton = {
                        onEditWorkerDataClick()
                    }
                )
            }
        }

        item {
            OperationButtonsBlock(
                onAddViolationClick = {},
                onCleanClick = {},
                onAdditionalParamsClick = {}
            )
        }

        items(state.violationMap?.values?.toList() ?: emptyList()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ViolationCard(
                    violation = it,
                    onChangeValueClick = {},
                    onResolveClick = {},
                    onDeleteClick = {},
                    onMakePhotoClick = {},
                    onMakeVideoClick = {},
                    onAddTagClick = {},
                    onItemClick = {}
                )
            }
        }
    }

}

@Composable
private fun OperationButtonsBlock(
    onAddViolationClick: () -> Unit,
    onCleanClick: () -> Unit,
    onAdditionalParamsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    ) {
        SquaredMediumButtonUnderneathText(
            modifier = Modifier.weight(1f),
            onClick = { onAddViolationClick() },
            text = stringResource(R.string.add_violation),
            icon = {
                Icon(
                    painterResource(R.drawable.ic_add_item),
                    contentDescription = null
                )
            }
        )
        SquaredMediumButtonUnderneathText(
            modifier = Modifier.weight(1f),
            onClick = { onCleanClick() },
            text = stringResource(R.string.clean_string),
            icon = {
                Icon(
                    painterResource(R.drawable.ic_clear_list),
                    contentDescription = null
                )
            }
        )
        SquaredMediumButtonUnderneathText(
            modifier = Modifier.weight(1f),
            onClick = { onAdditionalParamsClick() },
            text = stringResource(R.string.additional_params),
            icon = {
                Icon(
                    Icons.Default.ModeEdit,
                    contentDescription = null
                )
            }
        )
    }
}

@Composable
private fun CoachInfoBlock(
    modifier: Modifier = Modifier,
    state: CoachDraftState,
    onClickButton: () -> Unit
) {
    LabeledBlock(
        modifier = modifier,
        label = stringResource(R.string.coach_data)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .width(IntrinsicSize.Max),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DetailRow(label = stringResource(R.string.coach_number), value = state.number ?: "")
                DetailRow(label = stringResource(R.string.depot_string), value = state.depot ?: "")
                DetailRow(
                    label = stringResource(R.string.trailing_string),
                    value = if (state.isTrailing == true) stringResource(R.string.yes_string) else stringResource(
                        R.string.no_string
                    )
                )
            }
            SquaredMediumButton(
                onClick = { onClickButton() },
                text = "",
                icon = {
                    Icon(
                        Icons.Default.ModeEdit,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Composable
private fun WorkerInfoBlock(
    modifier: Modifier = Modifier,
    state: CoachDraftState,
    onClickButton: () -> Unit
) {
    LabeledBlock(
        modifier = modifier,
        label = stringResource(R.string.worker_data)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .width(IntrinsicSize.Max),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DetailRow(
                    label = stringResource(R.string.worker_id),
                    value = state.worker?.id ?: ""
                )
                DetailRow(
                    label = stringResource(R.string.worker_name),
                    value = state.worker?.name ?: ""
                )
                DetailRow(
                    label = stringResource(R.string.worker_depot),
                    value = state.worker?.depot ?: ""
                )
                DetailRow(
                    label = stringResource(R.string.worker_position),
                    value = state.worker?.position ?: ""
                )
            }
            SquaredMediumButton(
                onClick = { onClickButton() },
                text = "",
                icon = {
                    Icon(
                        Icons.Default.ModeEdit,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = label,
            style = AppTypography.bodyMedium,
            color = AppColors.MAIN_COLOR.color,
            modifier = Modifier.wrapContentWidth()
        )
        Text(
            text = value,
            style = AppTypography.bodyMedium,
            color = AppColors.MAIN_COLOR.color,
            modifier = Modifier.weight(1f)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    EditCoachOnMonitoringScreenContent(
//        innerPadding = PaddingValues(0.dp),
//        state = CoachDraftState(UUID.randomUUID(), CoachTypes.PASSENGER_CAR)
//    )
//}

//    val violations = listOf(
//        ViolationUi(
//            code = 100,
//            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
//                    "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
//                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
//                    "ut aliquip ex ea commodo consequat.\n",
//            shortDescription = "Test",
//            value = 1,
//            isResolved = false,
//            attributes = emptyList(),
//            mediaPaths = emptyList()
//        ),
//        ViolationUi(
//            code = 200,
//            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
//                    "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
//                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
//                    "ut aliquip ex ea commodo consequat.\n",
//            shortDescription = "Test",
//            value = 1,
//            isResolved = false,
//            attributes = emptyList(),
//            mediaPaths = emptyList()
//        )
//    )