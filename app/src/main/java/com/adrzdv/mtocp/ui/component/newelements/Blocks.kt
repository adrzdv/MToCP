package com.adrzdv.mtocp.ui.component.newelements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun InfoBlock(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = AppColors.MAIN_COLOR.color,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = AppColors.MAIN_COLOR.color,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = AppTypography.bodyLarge,
            color = AppColors.SURFACE_COLOR.color
        )
    }
}

@Composable
fun BlancInfoBlock(
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(
                color = AppColors.SURFACE_COLOR.color,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = AppColors.MAIN_COLOR.color,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        content
    }
}




