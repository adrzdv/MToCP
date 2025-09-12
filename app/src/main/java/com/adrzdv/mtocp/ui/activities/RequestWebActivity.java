package com.adrzdv.mtocp.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.ComposeView;

import com.adrzdv.mtocp.ui.screen.wrapper.RequetWebScreenWrapperKt;

import kotlin.Unit;

public class RequestWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ComposeView composeView = new ComposeView(this);
        RequetWebScreenWrapperKt.showRequestWebScreen(composeView,
                () -> {
                    finish();
                    return Unit.INSTANCE;
                });
        setContentView(composeView);

    }
}
