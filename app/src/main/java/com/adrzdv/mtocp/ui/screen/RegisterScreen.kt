package com.adrzdv.mtocp.ui.screen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.activities.StartMenuActivity
import com.adrzdv.mtocp.ui.component.CustomOutlinedTextField
import com.adrzdv.mtocp.ui.component.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.buttons.MediumMenuButton
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel
) {
    val state by viewModel.regState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var passwordVisibility by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(
        state.isSuccess
    ) {
        if (state.isSuccess) {
            val intent = Intent(context, StartMenuActivity::class.java)
            context.startActivity(intent)
            (context as? Activity)?.finish()
        }
    }

    LaunchedEffect(state.errorMessage) {
        val error = state.errorMessage
        error?.let {
            snackBarHostState.showSnackbar(message = it)
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
                .background(AppColors.BACKGROUND_COLOR.color)
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
                    }

                }
                Spacer(modifier = Modifier.weight(0.05f))
                Text(
                    text = stringResource(R.string.welcome),
                    style = AppTypography.titleMedium,
                    color = AppColors.SURFACE_COLOR.color
                )
                Text(
                    text = stringResource(R.string.greeting_message),
                    style = AppTypography.titleMedium,
                    color = AppColors.SURFACE_COLOR.color,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(0.05f))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.4f)
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomOutlinedTextField(
                        value = state.login,
                        onValueChange = {
                            viewModel.onLoginChange(it)
                        },
                        isEnabled = !state.isLoading,
                        isError = state.loginErrorRes != null,
                        errorText = state.loginErrorRes?.let { loginError ->
                            stringResource(id = loginError)
                        } ?: "",
                        label = stringResource(R.string.login),
                        modifier = Modifier.weight(1f),
                        trailingIcon = painterResource(R.drawable.ic_outline_person_edit_24),
                        secretInput = false
                    )
                    CustomOutlinedTextField(
                        value = state.password,
                        onValueChange = {
                            viewModel.onPasswordChange(it)
                        },
                        isEnabled = !state.isLoading,
                        isError = state.passwordErrorRes != null,
                        errorText = state.passwordErrorRes?.let { passwordError ->
                            stringResource(id = passwordError)
                        } ?: "",
                        label = stringResource(R.string.password),
                        modifier = Modifier.weight(1f),
                        trailingIcon = painterResource(R.drawable.ic_outline_lock_24),
                        secretInput = when (passwordVisibility) {
                            true -> true
                            else -> false
                        }
                    )
                    TextButton(
                        onClick = {
                            passwordVisibility = !passwordVisibility
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.show_password),
                            style = AppTypography.bodyMedium,
                            color = AppColors.MAIN_COLOR.color
                        )
                    }

                    MediumMenuButton(
                        onClick = {
                            viewModel.login()
                        },
                        icon = {},
                        text = stringResource(R.string.login_button),
                        isEnable = state.isFormValid && !state.isLoading,
                        color = AppColors.SECONDARY_COLOR.color
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
                    CircularProgressIndicator(color = AppColors.SURFACE.color)
                }
            }
        }
    }
}