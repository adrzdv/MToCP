package com.adrzdv.mtocp.ui.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.ComposeView;

import com.adrzdv.mtocp.App;
import com.adrzdv.mtocp.MessageCodes;
import com.adrzdv.mtocp.ui.screen.wrapper.ServiceScreenWrapperKt;
import com.adrzdv.mtocp.util.DirectoryHandler;

import kotlin.Unit;

public class ServiceMenuActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> filePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri fileUri = result.getData().getData();
                        if (fileUri != null) {
                            App.getImportManager().importFromJson(this, fileUri,
                                    message -> App.showToast(this, message));
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
            App.showToast(this, MessageCodes.FILE_MANAGER_ERROR.getMessageTitle());
        }
    }

    private void cleanDirs() {
        if (DirectoryHandler.cleanDirectories()) {
            App.showToast(this, MessageCodes.DIRECTORY_SUCCESS.getMessageTitle());
        } else {
            App.showToast(this, MessageCodes.DIRECTORY_FAIL.getMessageTitle());
        }
    }
}