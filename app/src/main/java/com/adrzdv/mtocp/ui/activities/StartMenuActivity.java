package com.adrzdv.mtocp.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adrzdv.mtocp.R;
import com.adrzdv.mtocp.databinding.ActivityStartMenuBinding;

public class StartMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityStartMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        binding = ActivityStartMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.startMenuLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.startRevisionButton.setOnClickListener(this);
        binding.openViolationCatalog.setOnClickListener(this);
        binding.serviceMenuButton.setOnClickListener(this);
        binding.help.setOnClickListener(this);
        binding.exitButton.setOnClickListener(this);

        setVersionText();

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.start_revision_button) {

        } else if (v.getId() == R.id.open_violation_catalog) {
            Intent intent = new Intent(this, ViolationCatalogActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.service_menu_button) {
            Intent intent = new Intent(this, ServiceMenuActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.help) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.exit_button) {
            finishAffinity();
        }

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