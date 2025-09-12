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

import com.adrzdv.mtocp.MessageCodes;
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
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                    }
                }
        );

        videoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                        setResult(Activity.RESULT_CANCELED);
                        finish();
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
                        Intent intent = new Intent();
                        intent.putExtra("result", MessageCodes.PERMISSION_ERROR.getMessageTitle());
                        setResult(Activity.RESULT_CANCELED, intent);
                        Log.e("MediaCreator", "ERROR: Permission denied");
                    }
                }
        );

        if (savedInstanceState == null) {
            String order = getIntent().getStringExtra("order");
            String coach = getIntent().getStringExtra("coach");
            int violation = getIntent().getIntExtra("violation", 0);
            String shortViolationName = getIntent().getStringExtra("violationShort");
            captureImage(order, coach, shortViolationName);
        }

    }

    public void captureImage(String orderNumber, String coachNumber, String shortViolationName) {
        if (checkPermissions()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mediaFile = mediaCreator.getPhotoFile(orderNumber, coachNumber, shortViolationName);
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
