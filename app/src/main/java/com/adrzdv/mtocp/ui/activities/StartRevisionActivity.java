package com.adrzdv.mtocp.ui.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.ComposeView;
import androidx.lifecycle.ViewModelProvider;

import com.adrzdv.mtocp.domain.model.enums.OrdersTypes;
import com.adrzdv.mtocp.ui.screen.wrapper.RevisionScreenWrapperKt;
import com.adrzdv.mtocp.ui.viewmodel.AutocompleteViewModel;
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel;
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel;
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactoryProvider;

import kotlin.Unit;

public class StartRevisionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        AutocompleteViewModel autocompleteViewModel = new ViewModelProvider(this,
                ViewModelFactoryProvider.provideFactory()).get(AutocompleteViewModel.class);
        OrderViewModel orderViewModel = new ViewModelProvider(this,
                ViewModelFactoryProvider.provideFactory()).get(OrderViewModel.class);
        DepotViewModel depotViewModel = new ViewModelProvider(this,
                ViewModelFactoryProvider.provideFactory()).get(DepotViewModel.class);

        ComposeView composeView = new ComposeView(this);
        RevisionScreenWrapperKt.showRevisionScreen(composeView,
                orderViewModel,
                depotViewModel,
                autocompleteViewModel,
                OrdersTypes.getTypeList(),
                () -> {
                    finish();
                    return Unit.INSTANCE;
                });
        setContentView(composeView);
    }
}
