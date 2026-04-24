package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(
    navController: NavHostController? = null,
    authViewModel: AuthViewModel
) {
    val state by authViewModel.regState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var passwordVisibility by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val loginBringIntoViewRequester = remember { BringIntoViewRequester() }
    val passwordBringIntoViewRequester = remember { BringIntoViewRequester() }

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
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(AppColors.SURFACE_COLOR.color)
        ) {
            val isCompactHeight = maxHeight < 700.dp
            val headerHeight = if (isCompactHeight) 248.dp else 340.dp
            val logoSize = if (isCompactHeight) 144.dp else 216.dp
            val contentHorizontalPadding = if (maxWidth < 380.dp) 20.dp else 32.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(headerHeight)
                        .padding(horizontal = 16.dp),
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
                            modifier = Modifier.size(logoSize),
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
                Spacer(modifier = Modifier.height(16.dp))
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
                Spacer(modifier = Modifier.height(if (isCompactHeight) 8.dp else 16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 560.dp)
                        .padding(horizontal = contentHorizontalPadding, vertical = 16.dp),
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .bringIntoViewRequester(loginBringIntoViewRequester)
                            .onFocusEvent { focusState ->
                                if (focusState.isFocused) {
                                    coroutineScope.launch {
                                        loginBringIntoViewRequester.bringIntoView()
                                    }
                                }
                            },
                        trailingIcon = { painterResource(R.drawable.ic_outline_person_edit_24) },
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .bringIntoViewRequester(passwordBringIntoViewRequester)
                            .onFocusEvent { focusState ->
                                if (focusState.isFocused) {
                                    coroutineScope.launch {
                                        passwordBringIntoViewRequester.bringIntoView()
                                    }
                                }
                            },
                        trailingIcon = { painterResource(R.drawable.ic_outline_lock_24) },
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
