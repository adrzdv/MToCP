package com.adrzdv.mtocp.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.adrzdv.mtocp.App;
import com.adrzdv.mtocp.util.MediaCreator;

import java.io.File;

public class CameraActivity extends AppCompatActivity {
    private File mediaFile;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> videoLauncher;
    private ActivityResultLauncher<String[]> permissionLauncher;
    private final MediaCreator mediaCreator = new MediaCreator(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        mediaCreator.overlayDataTime(mediaFile);
                        App.showToast(this, "Фото добавлено");
                        finish();
                    } else {
                        App.showToast(this, "Ошибка при создании фотографии");
                        finish();
                    }
                }
        );

        videoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        App.showToast(this, "Видео успешно добавлено");
                    } else {
                        App.showToast(this, "Ошибка при записи видео");
                    }
                }
        );

        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    boolean allGranted = result.values().stream().allMatch(granted -> granted);
                    if (allGranted) {
                        Log.d("MediaCreator", "PERMISSIONS: Accessed");
                    } else {
                        App.showToast(this, "ERROR: Permission denied");
                        Log.e("MediaCreator", "ERROR: Permission denied");
                    }
                }
        );

        if (savedInstanceState == null) {
            String order = getIntent().getStringExtra("order");
            String coach = getIntent().getStringExtra("coach");
            int violation = getIntent().getIntExtra("violation", 0);
            captureImage(order, coach, violation);
        }

    }

    public void captureImage(String orderNumber, String coachNumber, int violationCode) {
        if (checkPermissions()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mediaFile = mediaCreator.getPhotoFile(orderNumber, coachNumber, violationCode);
            if (mediaFile != null) {
                Uri fileUri = mediaCreator.gerUri();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                cameraLauncher.launch(takePictureIntent);
            }
        }
    }

    private boolean checkPermissions() {
        String[] permissions = {Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(permissions);
            return false;
        }
        return true;
    }
}
