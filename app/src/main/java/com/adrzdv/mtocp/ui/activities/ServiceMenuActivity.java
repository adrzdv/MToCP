package com.adrzdv.mtocp.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.adrzdv.mtocp.App;
import com.adrzdv.mtocp.R;
import com.adrzdv.mtocp.data.db.dao.ViolationDao;
import com.adrzdv.mtocp.data.repository.ViolationRepositoryImpl;
import com.adrzdv.mtocp.databinding.ActivityServiceMenuBinding;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactory;
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactoryProvider;
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel;
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModelFactory;
import com.adrzdv.mtocp.util.DirectoryHandler;

public class ServiceMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityServiceMenuBinding binding;
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private ViolationViewModel violationViewModel;

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

        ViewModelFactory factory = ViewModelFactoryProvider.provideFactory();
        violationViewModel = new ViewModelProvider(this, factory).get(ViolationViewModel.class);

        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri fileUri = result.getData().getData();
                        if (fileUri != null) {
                            //violationViewModel.importViolationFromJson(this, fileUri);

                            


                            violationViewModel.getToastMessage().observe(this, event -> {
                                String message = event.getContentIfNotHandled();
                                if (message != null) {
                                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
        );

        binding.cleanDirsButton.setOnClickListener(this);
        binding.loadCatalogButton.setOnClickListener(this);
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
            loadDataFromFile();
        }
    }

    private void loadDataFromFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        String[] mimeTypes = {"application/json", "text/json"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            filePickerLauncher.launch(Intent.createChooser(intent, "Выберите файл"));
        } catch (android.content.ActivityNotFoundException ex) {
            App.showToast(this, "Установите файловый менеджер.");
        }
    }
}