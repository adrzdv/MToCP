package com.adrzdv.mtocp.ui.viewmodel.service;

import static com.adrzdv.mtocp.MessageCodes.UNSUPPORTED_CLASS;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

public class CustomViewModelProvider implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<? extends ViewModel>> creators;

    public CustomViewModelProvider(Map<Class<? extends ViewModel>, Provider<? extends ViewModel>> creators) {
        this.creators = creators;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends ViewModel> creator = creators.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class<? extends ViewModel>, Provider<? extends ViewModel>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException(UNSUPPORTED_CLASS + ": " + modelClass);
        }
        return (T) creator.get();
    }
}
