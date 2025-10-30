package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.model.ChangePasswordResult
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.RoundedButton
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.ChangePasswordBottomSheetViewModel
import com.adrzdv.mtocp.ui.viewmodel.ServiceViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ChangePasswordBottomSheet(
    viewModel: ChangePasswordBottomSheetViewModel,
    serviceViewModel: ServiceViewModel,
    onDismiss: () -> Unit
) {
    val passwordRulesHint: List<String> = listOf(
        stringResource(R.string.password_rule_title),
        stringResource(R.string.password_rule_1),
        stringResource(R.string.password_rule_2),
        stringResource(R.string.password_rule_3),
        stringResource(R.string.password_rule_4)
    )

    val state = viewModel.state
    val isLoading by viewModel.isLoading.collectAsState()
    val result by viewModel.result.collectAsState()
    val success = stringResource(R.string.password_changed_success)
    val error = stringResource(R.string.error_password_change)

    LaunchedEffect(result) {
        result?.let { result ->
            when (result) {
                is ChangePasswordResult.Success -> {
                    serviceViewModel.showMessage(success)
                    onDismiss()
                }

                is ChangePasswordResult.ServerError -> {
                    serviceViewModel.showErrorMessage(error)
                    onDismiss()
                }

                is ChangePasswordResult.NetworkError -> {
                    serviceViewModel.showErrorMessage(error)
                    onDismiss()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.BACKGROUND_COLOR.color)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_outline_password_24),
            contentDescription = null,
            tint = AppColors.MAIN_COLOR.color,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.change_password),
            style = AppTypography.titleMedium,
            color = AppColors.MAIN_COLOR.color
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_information_24_white),
                contentDescription = null,
                tint = AppColors.MAIN_COLOR.color
            )
            Column {
                passwordRulesHint.forEach { hint ->
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    ) {
                        Text(
                            text = "\u2022",
                            style = AppTypography.bodySmall,
                            color = AppColors.MAIN_COLOR.color,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = hint,
                            style = AppTypography.bodySmall,
                            color = AppColors.MAIN_COLOR.color,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.newPassword,
            onValueChange = viewModel::onPasswordChange,
            isError = state.passwordHint?.isNotBlank() == true || state.isError,
            errorText = state.passwordHint ?: state.errorMessage,
            label = stringResource(R.string.password_new_hint),
            secretInput = !state.showPassword
        )
        Spacer(modifier = Modifier.height(8.dp))
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.confirmNewPassword,
            onValueChange = viewModel::onConfirmChange,
            isError = state.passwordHint?.isNotBlank() == true || state.isError,
            errorText = state.passwordHint ?: state.errorMessage,
            label = stringResource(R.string.password_confirm_hint),
            secretInput = !state.showPassword
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = {
                viewModel.togglePasswordVisibility()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = AppColors.MAIN_COLOR.color,
                containerColor = Color.Transparent,
                disabledContentColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.38f)
            )
        ) {
            Text(
                stringResource(R.string.show_password),
                style = AppTypography.labelLarge,
                color = AppColors.MAIN_COLOR.color
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = AppColors.MAIN_COLOR.color,
                    containerColor = Color.Transparent,
                    disabledContentColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.38f)
                )
            ) {
                Text(
                    stringResource(R.string.cancel),
                    style = AppTypography.labelLarge,
                    color = AppColors.MAIN_COLOR.color
                )
            }
            RoundedButton(
                onClick = {
                    viewModel.changePassword()
                },
                text = stringResource(R.string.save_string),
                isEnable = !isLoading
            )
        }
    }
}