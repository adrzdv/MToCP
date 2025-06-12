package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun InnerWorkerItemCard(worker: InnerWorkerDomain, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.OFF_WHITE.color,
            contentColor = AppColors.MAIN_GREEN.color
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    worker.name,
                    style = AppTypography.bodyLarge
                )
                val info = listOfNotNull(
                    worker.workerType?.description,
                    worker.depotDomain?.shortName
                ).joinToString(", ")
                if (info.isNotBlank()) {
                    Text(
                        info,
                        style = AppTypography.bodyMedium
                    )
                }
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete_32_white),
                    contentDescription = stringResource(R.string.delete)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InnerWorkerItemCardPreview() {
    val sampleWorker = InnerWorkerDomain(
        1,
        "Иванов Иван",
        null,
        null
    )

    InnerWorkerItemCard(
        worker = sampleWorker,
        onDeleteClick = {}
    )
}