package com.adrzdv.mtocp.ui.viewmodel.service

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adrzdv.mtocp.AppDependencies
import com.adrzdv.mtocp.ui.viewmodel.model.AdditionalParamViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.AuthViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.CompanyViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.KriCoachViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.OrderViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.ServiceViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.TrainInfoViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.ViolationViewModel

class ViewModelLocator(
    private val appDependencies: AppDependencies
) {
    private val factory = ViewModelFactoryProvider(appDependencies)

    fun getViolationViewModel(owner: ViewModelStoreOwner): ViolationViewModel {
        return ViewModelProvider(owner, factory.provideFactory())[ViolationViewModel::class.java]
    }

    fun getDepotViewModel(owner: ViewModelStoreOwner) =
        factory.provideFactory().create(DepotViewModel::class.java)

    fun getCompanyViewModel(owner: ViewModelStoreOwner) =
        factory.provideFactory().create(CompanyViewModel::class.java)

    fun getOrderViewModel(owner: ViewModelStoreOwner) =
        factory.provideFactory().create(OrderViewModel::class.java)

    fun getAutocompleteViewModel(owner: ViewModelStoreOwner) =
        factory.provideFactory().create(AutocompleteViewModel::class.java)

    fun getAdditionalParamViewModel(owner: ViewModelStoreOwner) =
        factory.provideFactory().create(AdditionalParamViewModel::class.java)

    fun getTrainInfoViewModel(owner: ViewModelStoreOwner) =
        factory.provideFactory().create(TrainInfoViewModel::class.java)

    fun getKriCoachViewModel(owner: ViewModelStoreOwner) =
        factory.provideFactory().create(KriCoachViewModel::class.java)

    fun getAuthViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(
            owner,
            AssistedViewModelFactory { AuthViewModel(appDependencies.authRepo) }
        )[AuthViewModel::class.java]

}