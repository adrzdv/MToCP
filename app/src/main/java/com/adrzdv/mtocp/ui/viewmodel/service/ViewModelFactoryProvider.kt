package com.adrzdv.mtocp.ui.viewmodel.service

import androidx.lifecycle.ViewModel
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.ui.viewmodel.model.KriCoachViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.TrainInfoViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.TrainOrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.old.AdditionalParamViewModel

class ViewModelFactoryProvider(
    private val appDependencies: AppDependencies
) {
    fun provideFactory(): CustomViewModelProvider {
        val creators: Map<Class<out ViewModel>, Provider<out ViewModel>> = mapOf(
            //ViolationViewModelOld::class.java to Provider { ViolationViewModelOld(appDependencies.violationRepo) },
            //DepotViewModel::class.java to Provider { DepotViewModel(appDependencies.depotRepo) },
            //CompanyViewModel::class.java to Provider { CompanyViewModel(appDependencies.companyRepo) },
            AdditionalParamViewModel::class.java to Provider {
                AdditionalParamViewModel(
                    appDependencies.tempParamRepo
                )
            },
            TrainInfoViewModel::class.java to Provider { TrainInfoViewModel(appDependencies.trainRepo) },
            KriCoachViewModel::class.java to Provider { KriCoachViewModel(appDependencies.kriCoachRepo) },
            TrainOrderViewModel::class.java to Provider {
                TrainOrderViewModel(
                    appDependencies,
                    appDependencies.getDepotByNameUseCase,
                    appDependencies.getTrainByNumberUseCase,
                    appDependencies.createPassengerCoachUseCase,
                    appDependencies.getTrainSchemeUseCase,
                    appDependencies.createDinnerCarUseCase
                )
            }
        )
        return CustomViewModelProvider(creators)
    }

}