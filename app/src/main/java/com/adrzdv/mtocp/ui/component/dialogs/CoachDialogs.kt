package com.adrzdv.mtocp.ui.component.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.departments.DepotDomain
import com.adrzdv.mtocp.domain.model.enums.DinnerCarsType
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar
import com.adrzdv.mtocp.ui.component.AppFullscreenDialog
import com.adrzdv.mtocp.ui.component.CustomOutlinedTextField
import com.adrzdv.mtocp.ui.component.DropdownMenuField
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.model.CompanyViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.old.OrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.RevisionObjectViewModel
import com.adrzdv.mtocp.ui.viewmodel.service.ViewModelFactoryProviderOld

@Composable
fun AddDinnerCarDialog(
    orderViewModel: OrderViewModel,
    depotViewModel: DepotViewModel,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var isTypeError by remember { mutableStateOf(false) }
    var isPatternError by remember { mutableStateOf(false) }
    var coachNumber by remember { mutableStateOf("") }
    var selectedDepot by remember { mutableStateOf("") }
    var selectedCompany by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<DinnerCarsType?>(null) }
    var isNumberError by remember { mutableStateOf(false) }
    var isDepotEmpty by remember { mutableStateOf(false) }
    var isDepotWhenCompanySelected by remember { mutableStateOf(false) }
    var isCompanyWhenDepotSelected by remember { mutableStateOf(false) }
    val companyViewModel: CompanyViewModel = viewModel(
        factory = ViewModelFactoryProviderOld.provideFactory()
    )
    companyViewModel.filterDinner()
    depotViewModel.filterDinner()

    fun addDinnerCar() {

        isNumberError = coachNumber.isEmpty()
        isTypeError = selectedType == null
        isDepotEmpty = selectedDepot.isEmpty() && selectedCompany.isEmpty()

        if (isNumberError || isTypeError || isDepotEmpty) {
            return
        }

        if (selectedDepot.isNotEmpty() && selectedCompany.isNotEmpty()) {
            isDepotWhenCompanySelected = true
            isCompanyWhenDepotSelected = true
            return
        }

        val revObject = DinnerCar(coachNumber)

        try {
            revObject.type = selectedType
            if (selectedDepot.isNotEmpty()) {
                revObject.depot = depotViewModel.getDepotDomain(selectedDepot)
            } else if (selectedCompany.isNotEmpty()) {
                revObject.companyDomain = companyViewModel.getCompanyDomain(selectedCompany)
            } else {
                throw IllegalArgumentException()
            }
            orderViewModel.addRevisionObject(revObject)
            orderViewModel.toggleDinnerCar(true)
            orderViewModel.updateTrainScheme()
            onConfirm()
        } catch (e: IllegalArgumentException) {
            isPatternError = true
        }
    }

    AppFullscreenDialog(
        title = stringResource(R.string.new_dinner),
        onConfirm = { addDinnerCar() },
        onDismiss = onDismiss,
        isSaveEnabled = true,
        content = {
            CustomOutlinedTextField(
                value = coachNumber,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    coachNumber = it
                    isNumberError = false
                    isPatternError = false
                },
                isError = isNumberError || isPatternError,
                errorText = when {
                    isNumberError -> stringResource(R.string.empty_string)
                    isPatternError -> MessageCodes.PATTERN_MATCHES_ERROR.messageTitle
                    else -> ""
                },
                label = stringResource(R.string.coach_number)
            )
            DropdownMenuField(
                label = stringResource(R.string.coach_type),
                isError = isTypeError,
                errorMessage = stringResource(R.string.empty_string),
                modifier = Modifier.fillMaxWidth(),
                options = DinnerCarsType.values().map { it.description },
                selectedOption = selectedType?.description ?: "",
                onOptionSelected = { selected ->
                    selectedType = DinnerCarsType.values().firstOrNull() {
                        it.description == selected
                    }
                    isTypeError = false
                }
            )

            //Для дирекции
            DropdownMenuField(
                label = stringResource(R.string.dinner_department),
                modifier = Modifier.fillMaxWidth(),
                isError = isDepotEmpty || isCompanyWhenDepotSelected,
                errorMessage = when {
                    isDepotEmpty -> stringResource(R.string.empty_string)
                    isCompanyWhenDepotSelected -> stringResource(R.string.parallel_selection_string)
                    else -> ""
                },
                options = depotViewModel.filteredDepots.value?.map { it.name } ?: emptyList(),
                selectedOption = selectedDepot,
                onOptionSelected = {
                    selectedDepot = it
                    isDepotWhenCompanySelected = false
                    isCompanyWhenDepotSelected = false
                    isDepotEmpty = false
                }
            )

            //Для арендаторов
            DropdownMenuField(
                label = stringResource(R.string.dinner_company),
                modifier = Modifier.fillMaxWidth(),
                isError = isDepotEmpty || isDepotWhenCompanySelected,
                errorMessage = when {
                    isDepotEmpty -> stringResource(R.string.empty_string)
                    isDepotWhenCompanySelected -> stringResource(R.string.parallel_selection_string)
                    else -> ""
                },
                options = companyViewModel.filteredCompanies.value?.map { it.name } ?: emptyList(),
                selectedOption = selectedCompany,
                onOptionSelected = {
                    selectedCompany = it
                    isDepotEmpty = false
                    isDepotWhenCompanySelected = false
                    isCompanyWhenDepotSelected = false
                }
            )
            TextButton(
                onClick = {
                    if (isCompanyWhenDepotSelected
                        || isDepotWhenCompanySelected
                        || selectedCompany.isNotEmpty()
                        || selectedDepot.isNotEmpty()
                    ) {
                        selectedDepot = ""
                        selectedCompany = ""
                        isDepotWhenCompanySelected = false
                        isCompanyWhenDepotSelected = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = AppColors.MAIN_GREEN.color,
                    containerColor = Color.Transparent,
                    disabledContentColor = AppColors.MAIN_GREEN.color.copy(alpha = 0.38f)
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clear_list),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.clean_string),
                    style = AppTypography.labelLarge,
                    color = Color.Black
                )
            }
        }
    )
}

@Composable
fun AddCoachDialog(
    orderViewModel: OrderViewModel,
    depotViewModel: DepotViewModel,
    onDismiss: () -> Unit,
    coachViewModel: RevisionObjectViewModel<PassengerCar>
) {

    depotViewModel.resetDinnerFilter()

    var coachNumber by remember { mutableStateOf("") }
    var selectedDepot by remember { mutableStateOf("") }
    var checkedTrailingCar by remember { mutableStateOf(false) }
    var trailingRoute by remember { mutableStateOf("") }
    var typeCoachSelected by remember { mutableStateOf<PassengerCoachType?>(null) }
    var isCoachPatterError by remember { mutableStateOf(false) }
    var isDuplicateCoachError by remember { mutableStateOf(false) }
    var isRouteError by remember { mutableStateOf(false) }
    var isDepotError by remember { mutableStateOf(false) }
    var isCoachTypeError by remember { mutableStateOf(false) }

    fun addCoach() {
        val revObject = PassengerCar(coachNumber)
        if (selectedDepot.isEmpty()) {
            isDepotError = true
            return
        }
        if (typeCoachSelected == null) {
            isCoachTypeError = true
            return
        }
        val depot: DepotDomain = depotViewModel.getDepotDomain(selectedDepot)
        revObject.depotDomain = depot
        revObject.trailing = checkedTrailingCar
        revObject.coachType = typeCoachSelected
        revObject.coachRoute = trailingRoute
        if (checkedTrailingCar && trailingRoute.isBlank()) {
            isRouteError = true
            return
        }
        try {
            orderViewModel.addRevisionObject(revObject)
            coachViewModel.addRevObject(revObject)
            orderViewModel.updateTrainScheme()
            onDismiss()
        } catch (e: IllegalArgumentException) {
            isCoachPatterError = true
        } catch (e: IllegalStateException) {
            isDuplicateCoachError = true
        }
    }

    AppFullscreenDialog(
        title = stringResource(R.string.new_coach),
        onConfirm = { addCoach() },
        onDismiss = onDismiss,
        isSaveEnabled = true,
        content = {
            CustomOutlinedTextField(
                value = coachNumber,
                onValueChange = {
                    coachNumber = it
                    isCoachPatterError = false
                    isDuplicateCoachError = false
                },
                isError = isCoachPatterError || isDuplicateCoachError,
                errorText = when {
                    isCoachPatterError -> MessageCodes.PATTERN_MATCHES_ERROR.messageTitle
                    isDuplicateCoachError -> MessageCodes.DUPLICATE_ERROR.messageTitle
                    else -> ""
                },
                label = stringResource(R.string.coach_number),
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenuField(
                label = stringResource(R.string.coach_type),
                isError = isCoachTypeError,
                errorMessage = stringResource(R.string.empty_string),
                options = PassengerCoachType.values().map { it.passengerCoachTitle },
                selectedOption = typeCoachSelected?.passengerCoachTitle ?: "",
                onOptionSelected = { selected ->
                    isDepotError = false
                    typeCoachSelected = PassengerCoachType.values().firstOrNull() {
                        it.passengerCoachTitle == selected
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenuField(
                label = stringResource(R.string.worker_depot),
                isError = isDepotError,
                errorMessage = stringResource(R.string.empty_string),
                options = depotViewModel.filteredDepots.value?.map { it.name } ?: emptyList(),
                selectedOption = selectedDepot,
                onOptionSelected = {
                    selectedDepot = it
                    isDepotError = false
                },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Checkbox(
                    checked = checkedTrailingCar,
                    colors = CheckboxDefaults.colors(
                        checkedColor = AppColors.MAIN_GREEN.color,
                        checkmarkColor = AppColors.OFF_WHITE.color
                    ),
                    onCheckedChange = { checkedTrailingCar = it }
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.trailing_string),
                    style = AppTypography.labelLarge,
                    color = Color.Black
                )
            }
            CustomOutlinedTextField(
                value = trailingRoute,
                isEnabled = checkedTrailingCar,
                onValueChange = {
                    trailingRoute = it
                    isRouteError = false
                },
                isError = isRouteError,
                errorText = stringResource(R.string.empty_string),
                label = stringResource(R.string.trailing_route),
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(
                onClick = {
                    coachNumber = ""
                    selectedDepot = ""
                    trailingRoute = ""
                    typeCoachSelected = null
                    checkedTrailingCar = false
                    isCoachTypeError = false
                    isRouteError = false
                    isDuplicateCoachError = false
                    isCoachPatterError = false
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = AppColors.MAIN_GREEN.color,
                    containerColor = Color.Transparent,
                    disabledContentColor = AppColors.MAIN_GREEN.color.copy(alpha = 0.38f)
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clear_list),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.clean_string),
                    style = AppTypography.labelLarge,
                    color = Color.Black
                )
            }
        }
    )
}