package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel

@Composable
fun ResultScreenFullViolationList(
    orderViewModel: OrderViewModel
) {
    val items = orderViewModel.globalViolationResult

    Scaffold(
        containerColor = AppColors.LIGHT_GRAY.color
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.full_violation_list),
                    style = AppTypography.bodyMedium
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = AppColors.OFF_WHITE.color,
                            contentColor = AppColors.MAIN_GREEN.color
                        )
                    ) {
                        Text(
                            style = AppTypography.bodyMedium,
                            text = item
                        )
                    }
                }
            }
        }
    }
}