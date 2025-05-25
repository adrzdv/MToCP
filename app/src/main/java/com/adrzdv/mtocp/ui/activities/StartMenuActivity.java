package com.adrzdv.mtocp.ui.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.ComposeView;

import com.adrzdv.mtocp.R;
import com.adrzdv.mtocp.databinding.ActivityStartMenuBinding;
import com.adrzdv.mtocp.ui.screen.MainScreenWrapperKt;

import kotlin.Unit;

public class StartMenuActivity extends AppCompatActivity {

    private ActivityStartMenuBinding binding;

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
                    // onOpenViolationCatalogClick
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
                    // onHelpClick
                    return Unit.INSTANCE;
                },
                versionName
        );

        setContentView(composeView);

    }

    private void setVersionText() {
        TextView versionText = findViewById(R.id.app_version_text);
        try {
            String version = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;
            String verRes = "ver." + version;
            versionText.setText(verRes);
        } catch (PackageManager.NameNotFoundException e) {
            versionText.setText("v?");
        }
    }

}