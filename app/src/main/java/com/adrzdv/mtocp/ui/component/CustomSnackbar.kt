package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun CustomSnackbarHost(
    hostState: SnackbarHostState,
) {
    SnackbarHost(
        hostState = hostState,
        snackbar = { snackbarData ->
            Snackbar(
                shape = RoundedCornerShape(4.dp),
                containerColor = AppColors.MATERIAL_RED.color,
                modifier = Modifier
                    .padding(8.dp),
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
                Text(
                    snackbarData.visuals.message,
                    style = AppTypography.labelLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}