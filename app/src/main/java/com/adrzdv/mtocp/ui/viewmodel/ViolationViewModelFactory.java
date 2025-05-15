package com.adrzdv.mtocp.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.adrzdv.mtocp.domain.repository.ViolationRepository;

public class ViolationViewModelFactory implements ViewModelProvider.Factory {

    private final ViolationRepository repository;

    public ViolationViewModelFactory(ViolationRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViolationViewModel.class)) {
            return (T) new ViolationViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
