package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.RoundedButton
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.snackbar.ErrorSnackbar
import com.adrzdv.mtocp.ui.navigation.Screen
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.model.AuthViewModel

@Composable
fun RegisterScreen(
    navController: NavHostController? = null,
    authViewModel: AuthViewModel
) {
    val state by authViewModel.regState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var passwordVisibility by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(
        state.isSuccess
    ) {
        if (state.isSuccess) {
            navController?.navigate(Screen.MainMenu.route)
        }
    }

    LaunchedEffect(state.errorMessage) {
        val error = state.errorMessage
        error?.let {
            snackBarHostState.showSnackbar(visuals = ErrorSnackbar(it))
        }
    }

    Scaffold(
        snackbarHost = {
            CustomSnackbarHost(
                hostState = snackBarHostState
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.SURFACE_COLOR.color)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    elevation = CardDefaults.cardElevation(8.dp),
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
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.app_logo),
                            modifier = Modifier.size(256.dp),
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(R.string.app_name),
                            style = AppTypography.titleLarge,
                            color = AppColors.SURFACE_COLOR.color
                        )
                        Text(
                            text = stringResource(R.string.app_title),
                            style = AppTypography.titleLarge,
                            color = AppColors.SURFACE_COLOR.color,
                            textAlign = TextAlign.Center
                        )
                    }

                }
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = stringResource(R.string.welcome),
                    style = AppTypography.titleMedium,
                    color = AppColors.MAIN_COLOR.color
                )
                Text(
                    text = stringResource(R.string.greeting_message),
                    style = AppTypography.titleMedium,
                    color = AppColors.MAIN_COLOR.color,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(0.05f))
                Column(
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InputTextField(
                        value = state.login,
                        onValueChange = {
                            authViewModel.onLoginChange(it)
                        },
                        isEnabled = !state.isLoading,
                        isError = state.loginErrorRes != null,
                        errorText = state.loginErrorRes?.let { loginError ->
                            stringResource(id = loginError)
                        } ?: "",
                        label = stringResource(R.string.login),
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = painterResource(R.drawable.ic_outline_person_edit_24),
                        secretInput = false
                    )
                    InputTextField(
                        value = state.password,
                        onValueChange = {
                            authViewModel.onPasswordChange(it)
                        },
                        isEnabled = !state.isLoading,
                        isError = state.passwordErrorRes != null,
                        errorText = state.passwordErrorRes?.let { passwordError ->
                            stringResource(id = passwordError)
                        } ?: "",
                        label = stringResource(R.string.password),
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = painterResource(R.drawable.ic_outline_lock_24),
                        secretInput = when (passwordVisibility) {
                            true -> true
                            else -> false
                        }
                    )
                    TextButton(
                        onClick = {
                            passwordVisibility = !passwordVisibility
                        },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = AppColors.MAIN_COLOR.color
                        ),
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        Text(
                            text = stringResource(R.string.show_password),
                            style = AppTypography.bodyMedium,
                            color = AppColors.MAIN_COLOR.color
                        )
                    }
                    RoundedButton(
                        onClick = {
                            authViewModel.login()
                        },
                        text = stringResource(R.string.login_button),
                        isEnable = state.isFormValid && !state.isLoading,
                        color = AppColors.MAIN_COLOR.color
                    )
                }
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = AppColors.MAIN_COLOR.color)
                }
            }
        }
    }
}