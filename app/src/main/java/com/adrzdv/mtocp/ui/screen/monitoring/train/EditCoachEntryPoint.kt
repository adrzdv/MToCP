package com.adrzdv.mtocp.ui.screen.monitoring.train

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.EditCoachViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.TrainOrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.service.AssistedViewModelFactory
import java.util.UUID

@Composable
fun EditCoachEntryPoint(
    coachId: String?,
    coachType: String?,
    backStackEntry: NavBackStackEntry,
    navController: NavHostController,
    appDependencies: AppDependencies,
    appCache: CacheRepository,
    trainOrderViewModel: TrainOrderViewModel,
    depotAutoCompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    workerAutoCompleteViewModel: AutocompleteViewModel<DepotWithBranch>,
    companyAutoCompleteViewModel: AutocompleteViewModel<CompanyWithBranch>
) {
    val orderState by trainOrderViewModel.orderState.collectAsState()
    val coach = coachId?.let {
        orderState.coachList[UUID.fromString(coachId)]
    }
    val editCoachViewModel: EditCoachViewModel = ViewModelProvider(
        backStackEntry,
        AssistedViewModelFactory {
            EditCoachViewModel(
                coach = coach,
                coachType = coachType,
                appCacheRepository = appCache,
                appDependencies = appDependencies
            )
        }
    )[EditCoachViewModel::class.java]

    EditCoachOnMonitoringScreen(
        navController,
        editCoachViewModel,
        appCache,
        depotAutoCompleteViewModel,
        workerAutoCompleteViewModel,
        companyAutoCompleteViewModel
    ) {
        navController.popBackStack()
    }
}