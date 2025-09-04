package com.adrzdv.mtocp.ui.component.buttons.newelements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun SquaredBigButton(
    onClick: () -> Unit,
    text: String,
    icon: Painter
) {
    Box(
        modifier = Modifier.height(100.dp).width(160.dp)
    ) {
        ElevatedButton(
            onClick = onClick,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            colors = ButtonDefaults
                .buttonColors(containerColor = AppColors.MAIN_COLOR.color),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxSize()
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
                    tint = AppColors.SURFACE_COLOR.color
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = text,
                    color = AppColors.SURFACE_COLOR.color,
                    textAlign = TextAlign.Center,
                    style = AppTypography.labelLarge
                )
            }
        }
    }
}