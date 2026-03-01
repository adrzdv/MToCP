package com.adrzdv.mtocp.ui.viewmodel.service;

import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.AppOld;
import com.adrzdv.mtocp.ui.viewmodel.model.AdditionalParamViewModel;
import com.adrzdv.mtocp.ui.viewmodel.model.AutocompleteViewModel;
import com.adrzdv.mtocp.ui.viewmodel.model.CompanyViewModel;
import com.adrzdv.mtocp.ui.viewmodel.model.DepotViewModel;
import com.adrzdv.mtocp.ui.viewmodel.model.KriCoachViewModel;
import com.adrzdv.mtocp.ui.viewmodel.model.old.OrderViewModel;
import com.adrzdv.mtocp.ui.viewmodel.model.TrainInfoViewModel;
import com.adrzdv.mtocp.ui.viewmodel.model.ViolationViewModel;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class ViewModelFactoryProviderOld {

    public static CustomViewModelProvider provideFactory() {

        Map<Class<? extends androidx.lifecycle.ViewModel>,
                Provider<? extends ViewModel>> creators = new HashMap<>();

        creators.put(ViolationViewModel.class,
                () -> new ViolationViewModel(AppOld.getInstance().getAppDependencies().getViolationRepo()));
        creators.put(DepotViewModel.class,
                () -> new DepotViewModel(AppOld.getInstance().getAppDependencies().getDepotRepo()));
        creators.put(CompanyViewModel.class,
                () -> new CompanyViewModel(AppOld.getInstance().getAppDependencies().getCompanyRepo()));
        creators.put(OrderViewModel.class,
                () -> new OrderViewModel(AppOld.getInstance().getAppDependencies().getTrainRepo()));
        creators.put(AutocompleteViewModel.class,
                () -> new AutocompleteViewModel(AppOld.getInstance().getAppDependencies().getTrainRepo()));
        creators.put(AdditionalParamViewModel.class,
                () -> new AdditionalParamViewModel(AppOld.getInstance().getAppDependencies().getTempParamRepo()));
        creators.put(TrainInfoViewModel.class,
                () -> new TrainInfoViewModel(AppOld.getInstance().getAppDependencies().getTrainRepo()));
        creators.put(KriCoachViewModel.class,
                () -> new KriCoachViewModel(AppOld.getInstance().getAppDependencies().getKriCoachRepo()));

        return new CustomViewModelProvider(creators);
    }
}
