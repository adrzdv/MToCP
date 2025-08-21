package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun MenuButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .width(140.dp)
    ) {
        ElevatedButton(
            onClick = onClick,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            colors = ButtonDefaults
                .buttonColors(containerColor = AppColors.MAIN_GREEN.color),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(bottom = 4.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = text,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = AppTypography.labelLarge
                )
            }
        }
    }
}

@Composable
fun CompactMenuButton(
    icon: Painter,
    onClick: () -> Unit,
    isEnabled: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(2.dp)
    ) {
        ElevatedButton(
            onClick = onClick,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            colors = ButtonDefaults
                .buttonColors(containerColor = AppColors.MAIN_GREEN.color),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = isEnabled
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun MediumMenuButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    text: String,
    isEnable: Boolean
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults
            .buttonColors(containerColor = AppColors.MAIN_GREEN.color),
        border = null,
        enabled = isEnable,
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon()
            Text(
                text = text,
                style = AppTypography.labelLarge
            )
        }
    }
}

@Composable
fun CustomOutlinedButton(
    onClick: () -> Unit,
    text: String
) {
    OutlinedButton(
        onClick = onClick,
        border = null,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = AppColors.MAIN_GREEN.color
        )
    ) {
        Text(
            text = text,
            style = AppTypography.bodyMedium
        )
    }
}