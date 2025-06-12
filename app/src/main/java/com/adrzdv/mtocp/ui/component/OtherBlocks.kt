package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun InfoRowBlock(
    label: String,
    description: String

) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            style = AppTypography.bodyLarge
        )

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = description,
            style = AppTypography.bodyLarge
        )
    }
}

@Composable
fun InfoBlockWithLabel(
    label: String,
    rows: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = AppColors.OUTLINE_GREEN.color,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .background(AppColors.LIGHT_GRAY.color)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                rows.forEach { (label, desc) ->
                    InfoRowBlock(label, desc)
                }
            }
        }

        Text(
            text = label,
            style = AppTypography.labelSmall,
            color = AppColors.OUTLINE_GREEN.color,
            modifier = Modifier
                .padding(start = 12.dp)
                .background(AppColors.LIGHT_GRAY.color)
                .padding(horizontal = 4.dp)
                .offset(y = (-4).dp)
        )
    }
}