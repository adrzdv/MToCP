package com.adrzdv.mtocp.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.App;
import com.adrzdv.mtocp.domain.repository.KriCoachRepo;

import java.util.HashMap;
import java.util.Map;

public class ViewModelFactoryProvider {

    public static CustomViewModelProvider provideFactory() {

        Map<Class<? extends androidx.lifecycle.ViewModel>,
                Provider<? extends ViewModel>> creators = new HashMap<>();

        creators.put(ViolationViewModel.class,
                () -> new ViolationViewModel(App.getViolationRepository()));
        creators.put(DepotViewModel.class,
                () -> new DepotViewModel(App.getDepotRepository()));
        creators.put(CompanyViewModel.class,
                () -> new CompanyViewModel(App.getCompanyRepository()));
        creators.put(OrderViewModel.class,
                () -> new OrderViewModel(App.getTrainRepository()));
        creators.put(AutocompleteViewModel.class,
                () -> new AutocompleteViewModel(App.getTrainRepository()));
        creators.put(AdditionalParamViewModel.class,
                () -> new AdditionalParamViewModel(App.getTempParamRepository()));
        creators.put(TrainInfoViewModel.class,
                () -> new TrainInfoViewModel(App.getTrainRepository()));
        creators.put(KriCoachViewModel.class,
                () -> new KriCoachViewModel(App.getKriCoachRepo()));

        return new CustomViewModelProvider(creators);
    }
}
