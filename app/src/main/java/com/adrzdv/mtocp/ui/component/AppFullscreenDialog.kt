package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppFullscreenDialog(
    title: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.LIGHT_GRAY.color),
            contentColor = AppColors.LIGHT_GRAY.color,
            shape = RectangleShape
        ) {
            Scaffold(
                containerColor = AppColors.LIGHT_GRAY.color,
                contentColor = AppColors.LIGHT_GRAY.color,
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = AppColors.LIGHT_GRAY.color
                        ),
                        title = {
                            Text(
                                text = title,
                                style = AppTypography.titleLarge
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = onDismiss
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = stringResource(R.string.cancel)
                                )
                            }
                        },
                        actions = {
                            TextButton(
                                onClick = onConfirm,
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = AppColors.MAIN_GREEN.color,
                                    containerColor = Color.Transparent,
                                    disabledContentColor = AppColors.MAIN_GREEN.color.copy(alpha = 0.38f)
                                )
                            ) {
                                Text(
                                    stringResource(R.string.save_string),
                                    style = AppTypography.labelLarge,
                                    color = AppColors.MAIN_GREEN.color
                                )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 12.dp)
                        .background(AppColors.LIGHT_GRAY.color),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    content()
                }
            }
        }
    }
}