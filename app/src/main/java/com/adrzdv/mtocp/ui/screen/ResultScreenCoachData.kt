package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.Coach
import com.adrzdv.mtocp.ui.component.CoachDropDownItemCard
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel

@Composable
fun ResultScreenCoachData(
    orderViewModel: OrderViewModel
) {
    val coaches = orderViewModel.objMapWithViolations.toList()
    Scaffold(
        containerColor = AppColors.LIGHT_GRAY.color
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(coaches) { coach ->
                    (coach as Coach).let { pCoach ->
                        coach.violationMap.isNotEmpty().let {
                            CoachDropDownItemCard(coach = pCoach)
                        }
                    }
                }
            }
        }
    }
}