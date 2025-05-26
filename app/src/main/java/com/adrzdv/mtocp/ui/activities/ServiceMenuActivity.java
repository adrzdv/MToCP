package com.adrzdv.mtocp.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.ComposeView;
import androidx.lifecycle.ViewModelProvider;

import com.adrzdv.mtocp.App;
import com.adrzdv.mtocp.ui.screen.wrapper.ServiceScreenWrapperKt;
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactoryProvider;
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel;
import com.adrzdv.mtocp.util.DirectoryHandler;

import kotlin.Unit;

public class ServiceMenuActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> filePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViolationViewModel violationViewModel = new ViewModelProvider(this, ViewModelFactoryProvider.provideFactory())
                .get(ViolationViewModel.class);

        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri fileUri = result.getData().getData();
                        if (fileUri != null) {
                            //violationViewModel.importViolationFromJson(this, fileUri);
                        }
                    }
                }
        );

        ComposeView composeView = new ComposeView(this);
        ServiceScreenWrapperKt.showServiceScreen(composeView,
                () -> {
                    cleanDirs();
                    return Unit.INSTANCE;
                },
                () -> {
                    loadDataFromFile();
                    return Unit.INSTANCE;
                },
                () -> {
                    finish();
                    return Unit.INSTANCE;
                });

        setContentView(composeView);
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

    private void cleanDirs() {
        if (DirectoryHandler.cleanDirectories()) {
            App.showToast(this, "Директории очищены");
        } else {
            App.showToast(this, "Ошибка очистки директорий");
        }
    }
}