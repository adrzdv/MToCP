package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.CustomOutlinedTextField
import com.adrzdv.mtocp.ui.component.buttons.MediumMenuButton
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun RegisterScreen() {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BACKGROUND_COLOR.color),
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
            text = "Welcome!",
            style = AppTypography.titleMedium,
            color = AppColors.SURFACE_COLOR.color
        )
        Text(
            text = "Please, input login and password",
            style = AppTypography.titleMedium,
            color = AppColors.SURFACE_COLOR.color
        )
        Spacer(modifier = Modifier.weight(0.05f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomOutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                },
                isEnabled = true,
                isError = false,
                errorText = "",
                label = stringResource(R.string.login),
                modifier = Modifier.weight(1f),
                trailingIcon = painterResource(R.drawable.ic_outline_person_edit_24)
            )
            CustomOutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                isEnabled = true,
                isError = false,
                errorText = "",
                label = stringResource(R.string.password),
                modifier = Modifier.weight(1f),
                trailingIcon = painterResource(R.drawable.ic_outline_lock_24)
            )
            MediumMenuButton(
                onClick = {},
                icon = {},
                text = stringResource(R.string.login_button),
                isEnable = true,
                color = AppColors.SECONDARY_COLOR.color
            )
        }
        Spacer(modifier = Modifier.weight(0.1f))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RegisterScreen()
}