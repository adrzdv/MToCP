package com.adrzdv.mtocp.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.ComposeView;

import com.adrzdv.mtocp.R;
import com.adrzdv.mtocp.ui.screen.MainScreenWrapperKt;

import kotlin.Unit;

public class StartMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String version;
        try {
            version = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            version = "unknown";
        }

        final String versionName = version;

        ComposeView composeView = new ComposeView(this);
        MainScreenWrapperKt.showStartMenuScreen(composeView,
                () -> {
                    // onStartRevisionClick
                    return Unit.INSTANCE;
                },
                () -> {
                    Intent intent = new Intent(this, ViolationCatalogActivity.class);
                    startActivity(intent);
                    return Unit.INSTANCE;
                },
                () -> {
                    // onServiceMenuClick
                    return Unit.INSTANCE;
                },
                () -> {
                    // onExitClick
                    finish();
                    return Unit.INSTANCE;
                },
                () -> {
                    Intent intent = new Intent(this, HelpActivity.class);
                    startActivity(intent);
                    return Unit.INSTANCE;
                },
                versionName
        );

        setContentView(composeView);

    }

}