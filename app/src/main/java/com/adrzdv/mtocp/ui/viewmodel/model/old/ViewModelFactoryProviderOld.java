package com.adrzdv.mtocp.ui.viewmodel.model.old;

import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.AppOld;
import com.adrzdv.mtocp.ui.viewmodel.model.CompanyViewModel;
import com.adrzdv.mtocp.ui.viewmodel.model.KriCoachViewModel;
import com.adrzdv.mtocp.ui.viewmodel.model.TrainInfoViewModel;
import com.adrzdv.mtocp.ui.viewmodel.service.CustomViewModelProvider;
import com.adrzdv.mtocp.ui.viewmodel.service.Provider;

import java.util.HashMap;
import java.util.Map;

@Deprecated(forRemoval = true)
public class ViewModelFactoryProviderOld {

    public static CustomViewModelProvider provideFactory() {

        Map<Class<? extends androidx.lifecycle.ViewModel>,
                Provider<? extends ViewModel>> creators = new HashMap<>();

//        creators.put(ViolationViewModelOld.class,
//                () -> new ViolationViewModelOld(AppOld.getInstance().getAppDependencies().getViolationRepo()));
//        creators.put(DepotViewModel.class,
//                () -> new DepotViewModel(AppOld.getInstance().getAppDependencies().getDepotRepo()));
        creators.put(CompanyViewModel.class,
//                () -> new CompanyViewModel(AppOld.getInstance().getAppDependencies().getCompanyRepo()));
//        creators.put(AutocompleteViewModel.class,
//                () -> new AutocompleteViewModel(AppOld.getInstance().getAppDependencies().getTrainRepo()));
//        creators.put(AdditionalParamViewModel.class,
                () -> new AdditionalParamViewModel(AppOld.getInstance().getAppDependencies().getTempParamRepo()));
        creators.put(TrainInfoViewModel.class,
                () -> new TrainInfoViewModel(AppOld.getInstance().getAppDependencies().getTrainRepo()));
        creators.put(KriCoachViewModel.class,
                () -> new KriCoachViewModel(AppOld.getInstance().getAppDependencies().getKriCoachRepo()));

        return new CustomViewModelProvider(creators);
    }
}
