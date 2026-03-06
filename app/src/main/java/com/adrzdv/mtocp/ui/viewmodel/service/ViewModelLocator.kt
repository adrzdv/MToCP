package com.adrzdv.mtocp.ui.viewmodel.service

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.ui.viewmodel.model.AuthViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.CompanyViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.KriCoachViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.TrainInfoViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.TrainOrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.ViolationViewModel

class ViewModelLocator(
    private val appDependencies: AppDependencies
) {
    private val factory = ViewModelFactoryProvider(appDependencies)

    fun getTrainOrderViewModel(owner: ViewModelStoreOwner): TrainOrderViewModel {
        return ViewModelProvider(owner, factory.provideFactory())[TrainOrderViewModel::class.java]
    }

    fun getViolationViewModel(owner: ViewModelStoreOwner): ViolationViewModel {
        return ViewModelProvider(owner, factory.provideFactory())[ViolationViewModel::class.java]
    }

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
            AssistedViewModelFactory { AutocompleteViewModel(appDependencies.trainRepo) }
        )["trainAutocompleteViewModel", AutocompleteViewModel::class.java]

    fun getDepotAutocompleteViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(
            owner,
            AssistedViewModelFactory { AutocompleteViewModel(appDependencies.depotRepo) }
        )["depotAutocompleteViewModel", AutocompleteViewModel::class.java]
}