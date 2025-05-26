package com.adrzdv.mtocp.ui.activities;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.ComposeView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adrzdv.mtocp.R;
import com.adrzdv.mtocp.ui.screen.HelpScreenWrapperKt;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ComposeView composeView = new ComposeView(this);
        HelpScreenWrapperKt.showHelpScreen(composeView);
        setContentView(composeView);

    }
}