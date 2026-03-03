package com.adrzdv.mtocp.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.ui.component.buttons.SplitButton
import com.adrzdv.mtocp.ui.component.newelements.AutocompleteField
import com.adrzdv.mtocp.ui.component.newelements.BlancInfoBlock
import com.adrzdv.mtocp.ui.component.newelements.DatePickerReadOnlyField
import com.adrzdv.mtocp.ui.component.newelements.DropdownField
import com.adrzdv.mtocp.ui.component.newelements.InfoBlock
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.cards.WorkerCard
import com.adrzdv.mtocp.ui.state.order.PickerField
import com.adrzdv.mtocp.ui.state.order.TrainOrderState
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.TrainOrderViewModel
import java.time.format.DateTimeFormatter

@Composable
fun InitDataScreenContent(
    state: TrainOrderState,
    trainOrderViewModel: TrainOrderViewModel,
    autocompleteViewModel: AutocompleteViewModel,
    navController: NavHostController,
    innerPadding: PaddingValues,
    formatter: DateTimeFormatter
) {
    val query by autocompleteViewModel.query.collectAsState()
    val suggestions by autocompleteViewModel.filteredItems.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        item {
            InfoBlock(
                text = stringResource(R.string.revision_start_description)
            )
        }
        item {
            InputTextField(
                value = state.orderNumber,
                trailingIcon = {
                    if (state.orderNumber.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                trainOrderViewModel.onNumberChange("")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.clear_text),
                                tint = AppColors.MAIN_COLOR.color
                            )
                        }
                    }
                },
                onValueChange = { trainOrderViewModel.onNumberChange(it) },
                isError = state.numberError?.isNotEmpty() == true,
                errorText = state.numberError,
                label = stringResource(R.string.order_number_hint)
            )
        }
        item {
            DropdownField(
                source = RevisionType.getMovableTypes(),
                selected = state.orderConditions?.revisionTypeTitle ?: "",
                isError = state.conditionsError?.isNotEmpty() == true,
                errorMessage = state.conditionsError,
                onOptionSelected = {
                    trainOrderViewModel.onConditionsChange(it)
                },
                label = stringResource(R.string.label_choose_revtype)
            )
        }
        item {
            DatePickerReadOnlyField(
                value = state.dateStart.format(formatter),
                label = stringResource(R.string.order_start_date_hint),
                isError = state.dateStartError?.isNotEmpty() == true,
                error = state.dateStartError,
                onClick = {
                    trainOrderViewModel.onPickDate(PickerField.START_DATE)
                }
            )
        }
        item {
            DatePickerReadOnlyField(
                value = state.dateEnd.format(formatter),
                label = stringResource(R.string.order_end_date_hint),
                isError = state.dateEndError?.isNotEmpty() == true,
                error = state.dateEndError,
                onClick = {
                    trainOrderViewModel.onPickDate(PickerField.END_DATE)
                }
            )
        }
        item {
            AutocompleteField(
                value = query,
                source = suggestions,
                onValueChange = { input ->
                    autocompleteViewModel.onQueryChange(input)
                },
                onValueSelected = { selected ->
                    trainOrderViewModel.onTrainSelected(selected)
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                autocompleteViewModel.onQueryChange("")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.clear_text),
                                tint = AppColors.MAIN_COLOR.color
                            )
                        }
                    }
                },
                label = stringResource(R.string.search_train),
                isError = !state.emptyTrainError.isNullOrEmpty(),
                enabled = true,
                error = state.emptyTrainError
            )
        }
        item {
            InputTextField(
                value = state.route,
                trailingIcon = {
                    if (state.route.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                trainOrderViewModel.onOrderRouteChange("")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.clear_text),
                                tint = AppColors.MAIN_COLOR.color
                            )
                        }
                    }
                },
                onValueChange = { trainOrderViewModel.onOrderRouteChange(it) },
                isError = state.routeError?.isNotEmpty() == true,
                errorText = state.routeError,
                label = stringResource(R.string.order_route)
            )
        }
        item {
            BlancInfoBlock {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.quality_passport),
                        color = AppColors.MAIN_COLOR.color,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )
                    Checkbox(
                        checked = state.isQualityPassport,
                        colors = CheckboxDefaults.colors(
                            checkedColor = AppColors.MAIN_COLOR.color,
                            uncheckedColor = AppColors.MAIN_COLOR.color,
                            checkmarkColor = AppColors.SURFACE_COLOR.color
                        ),
                        onCheckedChange = { trainOrderViewModel.onQualityPassportChange(it) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.train_crew),
                        color = AppColors.MAIN_COLOR.color,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )
                    SplitButton(
                        actions = mapOf(
                            stringResource(R.string.add_string) to Pair(
                                painterResource(R.drawable.ic_add_person),
                                { }
                            ),
                            stringResource(R.string.clean_string) to Pair(
                                painterResource(R.drawable.ic_clear_list),
                                { }
                            )
                        )
                    )
                }
            }
        }
        items(state.crewList.values.toList()) { worker ->
            WorkerCard(
                worker = worker,
                onDeleteClick = { }
            )
        }
    }
}