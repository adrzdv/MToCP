package com.adrzdv.mtocp.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.adrzdv.mtocp.App;

public class CustomViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViolationViewModel.class)) {
            return (T) new ViolationViewModel(App.getViolationRepository());
        } else if (modelClass.isAssignableFrom(DepotViewModel.class)) {
            return (T) new DepotViewModel(App.getDepotRepository());
        } else if (modelClass.isAssignableFrom(CompanyViewModel.class)) {
            return (T) new CompanyViewModel(App.getCompanyRepository());
        } else if (modelClass.isAssignableFrom(OrderViewModel.class)) {
            return (T) new OrderViewModel(App.getTrainRepository());
        } else if (modelClass.isAssignableFrom(AutocompleteViewModel.class)) {
            return (T) new AutocompleteViewModel(App.getTrainRepository());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
