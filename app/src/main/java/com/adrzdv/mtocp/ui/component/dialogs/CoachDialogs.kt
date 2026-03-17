package com.adrzdv.mtocp.ui.component.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.domain.model.enums.CoachTypes
import com.adrzdv.mtocp.mapper.toUI
import com.adrzdv.mtocp.ui.component.AppBar
import com.adrzdv.mtocp.ui.component.dialogs.newcoachcontent.NewDinnerCarContent
import com.adrzdv.mtocp.ui.component.dialogs.newcoachcontent.NewPassengerCoachContent
import com.adrzdv.mtocp.ui.component.newelements.NothingToShowPlug
import com.adrzdv.mtocp.ui.model.dto.CoachUIBase
import com.adrzdv.mtocp.ui.state.coach.CoachStateFactory
import com.adrzdv.mtocp.ui.state.coach.NewDinnerCoachState
import com.adrzdv.mtocp.ui.state.coach.NewPassengerCoachState
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCoach(
    selectedType: CoachTypes,
    onConfirm: (CoachUIBase) -> Unit,
    onDismiss: () -> Unit,
    depotAutocompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    workerDepotAutocompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    companyAutocompleteViewModel: AutocompleteViewModel<CompanyWithBranch>,
    appDependencies: AppDependencies
) {
    var state by remember { mutableStateOf(CoachStateFactory.create(selectedType)) }
    val stringProvider = appDependencies.stringProvider

    LaunchedEffect(Unit) {
        depotAutocompleteViewModel.resetQuery()
        workerDepotAutocompleteViewModel.resetQuery()
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.SURFACE_COLOR.color),
            contentColor = AppColors.SURFACE_COLOR.color,
            shape = RectangleShape
        ) {
            Scaffold(
                containerColor = AppColors.SURFACE_COLOR.color,
                contentColor = AppColors.SURFACE_COLOR.color,
                topBar = {
                    AppBar(
                        title = stringResource(R.string.new_coach),
                        actions = {
                            IconButton(
                                onClick = {
                                    val validatedState = state.validate(stringProvider)
                                    state = validatedState
                                    if (validatedState.isSaveEnabled()) {
                                        when (selectedType) {
                                            CoachTypes.PASSENGER_CAR -> onConfirm((validatedState as NewPassengerCoachState).toUI())
                                            CoachTypes.DINNER_CAR -> onConfirm((validatedState as NewDinnerCoachState).toUI())
                                            CoachTypes.COMMERCIAL_CAR -> {}
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_save_32_white),
                                    contentDescription = stringResource(R.string.save_string),
                                    tint = AppColors.SURFACE_COLOR.color
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = onDismiss
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = stringResource(R.string.cancel),
                                    tint = AppColors.SURFACE_COLOR.color
                                )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                when (selectedType) {
                    CoachTypes.PASSENGER_CAR -> {
                        NewPassengerCoachContent(
                            innerPadding,
                            state as NewPassengerCoachState,
                            { state = it },
                            workerDepotAutocompleteViewModel,
                            depotAutocompleteViewModel
                        )
                    }

                    CoachTypes.DINNER_CAR -> {
                        NewDinnerCarContent(
                            innerPadding,
                            state as NewDinnerCoachState,
                            { state = it },
                            depotAutocompleteViewModel,
                            companyAutocompleteViewModel
                        )
                    }

                    CoachTypes.COMMERCIAL_CAR -> {
                        NothingToShowPlug()
                    }
                }
            }
        }
    }
}