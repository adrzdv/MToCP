package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.ui.theme.CustomTypography

@Composable
fun MediumMenuButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    text: String
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults
            .buttonColors(containerColor = Color(0xFF4CAF50)),
        border = null
    ) {
        icon()
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = CustomTypography.labelLarge
        )

    }
}

@Preview
@Composable
fun ButtonPreview()
{
    MediumMenuButton(
        onClick = {},
        icon = {},
        text = "TEXT"
    )
}