package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

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
                .background(AppColors.OFF_WHITE.color)
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
                .background(AppColors.OFF_WHITE.color)
                .padding(horizontal = 4.dp)
        )
    }
}

@Preview
@Composable
fun PreviewInfoBlock() {
    val rows = listOf(
        "Sample" to "SamTxt",
        "SSS" to "TstST"
    )

    InfoBlockWithLabel(
        "Label",
        rows
        //"TEXT"
    )
}