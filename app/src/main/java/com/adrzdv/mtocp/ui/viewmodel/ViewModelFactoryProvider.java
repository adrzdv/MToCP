package com.adrzdv.mtocp.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.App;

import java.util.HashMap;
import java.util.Map;

public class ViewModelFactoryProvider {

    public static ViewModelFactory provideFactory() {

        Map<Class<? extends androidx.lifecycle.ViewModel>,
                Provider<? extends ViewModel>> creators = new HashMap<>();

        creators.put(ViolationViewModel.class,
                () -> new ViolationViewModel((App.getViolationRepository())));
        creators.put(DepotWithBranchViewModel.class,
                () -> new DepotWithBranchViewModel(App.getDepotRepository()));

        return new ViewModelFactory(creators);
    }


}
