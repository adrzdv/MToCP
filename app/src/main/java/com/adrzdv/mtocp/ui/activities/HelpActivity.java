package com.adrzdv.mtocp.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.ComposeView;

import com.adrzdv.mtocp.ui.screen.wrapper.HelpScreenWrapperKt;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ComposeView composeView = new ComposeView(this);
        HelpScreenWrapperKt.showHelpScreen(composeView);
        setContentView(composeView);

    }
}