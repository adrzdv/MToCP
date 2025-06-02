package com.adrzdv.mtocp.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.adrzdv.mtocp.App;

public class ViolationViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViolationViewModel.class)) {
            return (T) new ViolationViewModel(App.getViolationRepository());
        } else if (modelClass.isAssignableFrom(DepotViewModel.class)) {
            return (T) new DepotViewModel(App.getDepotRepository());
        } else if(modelClass.isAssignableFrom(CompanyViewModel.class)) {
            return (T) new CompanyViewModel(App.getCompanyRepository());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
