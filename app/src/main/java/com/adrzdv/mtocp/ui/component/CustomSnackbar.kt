package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
                containerColor = AppColors.ERROR.color,
                contentColor = Color.White,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .widthIn(min = 120.dp, max = 420.dp),
                action = {
                    snackbarData.visuals.actionLabel?.let {
                        TextButton(onClick = {
                            snackbarData.dismiss()
                        }) {
                            Text(text = it, color = Color.White)
                        }
                    }
                }
            ) {
                Text(
                    snackbarData.visuals.message,
                    color = Color.White,
                    style = AppTypography.labelLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}