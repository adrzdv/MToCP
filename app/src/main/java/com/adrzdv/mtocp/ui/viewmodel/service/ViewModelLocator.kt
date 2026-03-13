package com.adrzdv.mtocp.ui.viewmodel.service

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.data.db.entity.TrainEntity
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import com.adrzdv.mtocp.ui.viewmodel.model.AuthViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.CompanyViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.KriCoachViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.TrainInfoViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.TrainOrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.ViolationViewModel

class ViewModelLocator(
    private val appDependencies: AppDependencies,
    private val appCache: CacheRepository
) {
    private val factory = ViewModelFactoryProvider(appDependencies)

    fun getTrainOrderViewModel(owner: ViewModelStoreOwner): TrainOrderViewModel {
        return ViewModelProvider(owner, factory.provideFactory())[TrainOrderViewModel::class.java]
    }

//    fun getViolationViewModel(owner: ViewModelStoreOwner): ViolationViewModel {
//        return ViewModelProvider(owner, factory.provideFactory())[ViolationViewModel::class.java]
//    }

    fun getDepotViewModel(owner: ViewModelStoreOwner): DepotViewModel {
        return ViewModelProvider(owner, factory.provideFactory())[DepotViewModel::class.java]
    }

    fun getCompanyViewModel(owner: ViewModelStoreOwner): CompanyViewModel {
        return ViewModelProvider(owner, factory.provideFactory())[CompanyViewModel::class.java]
    }

    fun getTrainInfoViewModel(owner: ViewModelStoreOwner): TrainInfoViewModel {
        return ViewModelProvider(owner, factory.provideFactory())[TrainInfoViewModel::class.java]
    }

    fun getKriCoachViewModel(owner: ViewModelStoreOwner) =
        factory.provideFactory().create(KriCoachViewModel::class.java)

    fun getAuthViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(
            owner,
            AssistedViewModelFactory { AuthViewModel(appDependencies.authRepo) }
        )[AuthViewModel::class.java]

    fun getTainAutocompleteViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(
            owner,
            AssistedViewModelFactory {
                AutocompleteViewModel(
                    appDependencies.trainRepo,
                    TrainEntity::toString
                )
            }
        )["trainAutocompleteViewModel", AutocompleteViewModel::class.java] as AutocompleteViewModel<TrainEntity>

    fun getDepotAutocompleteViewModel(owner: ViewModelStoreOwner):
            AutocompleteViewModel<DepotWithBranch> =
        ViewModelProvider(
            owner,
            AssistedViewModelFactory {
                AutocompleteViewModel(
                    appDependencies.depotRepo,
                    DepotWithBranch::toString
                )
            }
        )["depotAutocompleteViewModel", AutocompleteViewModel::class.java] as AutocompleteViewModel<DepotWithBranch>

    fun getWorkerDepotAutocompleteViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(
            owner,
            AssistedViewModelFactory {
                AutocompleteViewModel(
                    appDependencies.depotRepo,
                    DepotWithBranch::toString
                )
            }
        )["workerDepotAutocompleteViewModel", AutocompleteViewModel::class.java] as AutocompleteViewModel<DepotWithBranch>

//    fun violationAutocompleteViewModel(owner: ViewModelStoreOwner) =
//        ViewModelProvider(
//            owner,
//            AssistedViewModelFactory {
//                AutocompleteViewModel(
//                    appDependencies.violationRepo,
//                    ViolationEntity::toString
//                )
//            }
//        )["violationAutocompleteViewModel", AutocompleteViewModel::class.java] as AutocompleteViewModel<ViolationEntity>

    fun getViolationViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(
            owner,
            AssistedViewModelFactory {
                ViolationViewModel(
                    appCache
                )
            }
        )[ViolationViewModel::class.java]
}