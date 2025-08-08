package com.adrzdv.mtocp.ui.screen

import androidx.compose.runtime.Composable
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel

@Composable
fun MonitoringDinnerScreen(
    orderViewModel: OrderViewModel
) {
    val dinnerCar = (orderViewModel.collector as? TrainDomain)?.dinnerCar


}