package com.adrzdv.mtocp.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.ComposeView;
import androidx.lifecycle.ViewModelProvider;

import com.adrzdv.mtocp.domain.model.enums.RevisionType;
import com.adrzdv.mtocp.ui.screen.wrapper.ViolationCatalogScreenWrapperKt;
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactoryProvider;
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel;

import kotlin.Unit;


public class ViolationCatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViolationViewModel viewModel = new ViewModelProvider(
                this,
                ViewModelFactoryProvider.provideFactory()
        ).get(ViolationViewModel.class);

        ComposeView composeView = new ComposeView(this);
        ViolationCatalogScreenWrapperKt.showViolationCatalogScreen(composeView,
                () -> {
                    finish();
                    return Unit.INSTANCE;
                },
                viewModel,
                RevisionType.getListOfTypes());

        setContentView(composeView);

    }
}