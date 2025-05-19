package com.adrzdv.mtocp.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adrzdv.mtocp.App;
import com.adrzdv.mtocp.R;
import com.adrzdv.mtocp.databinding.ActivityServiceMenuBinding;
import com.adrzdv.mtocp.databinding.ActivityStartMenuBinding;
import com.adrzdv.mtocp.util.DirectoryHandler;

public class ServiceMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityServiceMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        binding = ActivityServiceMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.cleanDirsButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.clean_dirs_button) {
            if (DirectoryHandler.cleanDirectories()) {
                App.showToast(this, "Директории очищены");
            } else {
                App.showToast(this, "Ошибка очистки директорий");
            }
        } else if (v.getId() == R.id.load_catalog_button) {
            return;
        }
    }
}