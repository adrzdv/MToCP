package com.adrzdv.mtocp.ui.component.newelements.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.model.TrainDto
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun TrainInfoCard(
    train: TrainDto
) {
    val isCCTV = train.video
    val isProgressNorm = train.progressive

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.SURFACE_COLOR.color,
            contentColor = AppColors.MAIN_COLOR.color
        )
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier.weight(0.75f)
            ) {
                Text(
                    text = train.number,
                    style = AppTypography.bodyLarge
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = train.route,
                    style = AppTypography.bodyLarge
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = listOfNotNull(
                        train.depotShortName,
                        train.branchShortName
                    ).joinToString(","),
                    style = AppTypography.bodyMedium
                )
            }
            Column (
                modifier = Modifier.weight(0.25f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_cctv),
                        contentDescription = null,
                        tint = if (isCCTV) AppColors.MAIN_COLOR.color else Color.Gray.copy(alpha = 0.5f)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_progressive_service),
                        contentDescription = null,
                        tint = if (isProgressNorm) AppColors.MAIN_COLOR.color else Color.Gray.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    var train = TrainDto(
        "001A", "route", "Depot",
        "Dep", "Branch", "Br", "0",
        true, false, false
    )
    TrainInfoCard(train)
}
