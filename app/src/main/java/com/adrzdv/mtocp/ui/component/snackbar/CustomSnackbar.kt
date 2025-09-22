package com.adrzdv.mtocp.ui.component.snackbar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun CustomSnackbarHost(
    hostState: SnackbarHostState
) {
    SnackbarHost(
        hostState = hostState,
        snackbar = { snackbarData ->
            val isError = snackbarData.visuals is ErrorSnackbar
            val isInfo = snackbarData.visuals is InfoSnackbar
            Snackbar(
                shape = RoundedCornerShape(4.dp),
                containerColor = AppColors.SURFACE_COLOR.color,
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = when {
                            isError -> AppColors.ERROR_COLOR.color
                            isInfo -> AppColors.MAIN_COLOR.color
                            else -> AppColors.SURFACE_COLOR.color
                        },
                        shape = RoundedCornerShape(4.dp)
                    ),
                action = {
                    snackbarData.visuals.actionLabel?.let {
                        TextButton(onClick = {
                            snackbarData.dismiss()
                        }) {
                            Text(
                                text = it
                            )
                        }
                    }
                }
            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_information_24_white),
                            tint = when {
                                isError -> AppColors.ERROR_COLOR.color
                                else -> AppColors.MAIN_COLOR.color
                            },
                            contentDescription = null
                        )
                        Text(
                            snackbarData.visuals.message,
                            style = AppTypography.labelLarge,
                            color = when {
                                isError -> AppColors.ERROR_COLOR.color
                                isInfo -> AppColors.MAIN_COLOR.color
                                else -> AppColors.SURFACE_COLOR.color
                            },
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    )
}