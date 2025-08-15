package com.adrzdv.mtocp.util;

import static com.adrzdv.mtocp.util.DirectoryHandler.MEDIA_DIRECTORY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

public class MediaCreator {
    private final String IMG_PREFIX = "IMG_";
    private final String VID_PREFIX = "VID_";
    private final String IMG_EXT = ".jpg";
    private final String VID_EXT = ".mp4";
    private final Context context;
    private Uri fileUri;
    private File mediaFile;

    public MediaCreator(Context context) {
        this.context = context;
    }

    public File getPhotoFile(String orderNumber, String coachNumber, String shortViolationName) {
        mediaFile = createPhotoFile(orderNumber, coachNumber, IMG_PREFIX + shortViolationName, IMG_EXT);
        return mediaFile;
    }

    public Uri gerUri() {
        this.fileUri = FileProvider.getUriForFile(context, context.getPackageName() +
                ".provider", mediaFile);
        return fileUri;
    }

    public File createPhotoFile(String orderNumber, String coachNumber, String prefix, String extension) {
        String dirName = makeDirName(orderNumber);
        File mediaDir = new File(MEDIA_DIRECTORY + "/" + dirName + "/" + coachNumber);
        if (!mediaDir.exists() && !mediaDir.mkdirs()) {
            Log.e("MediaCreator", "Access denied. Cant create a folder");
            return null;
        }
        String timestamp = new SimpleDateFormat("HH-mm-ss", Locale.getDefault()).format(new Date());
        return new File(mediaDir, prefix + "_" + timestamp + "_" + extension);
    }

    public void overlayDataTime(File imgFile) {
        if (imgFile == null || !imgFile.exists()) {
            Log.e("MediaCreator", "File not found!");
            return;
        }

        try {

            Bitmap resizedBitmap = compressPhoto(imgFile);

            Canvas canvas = new Canvas(resizedBitmap);
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            paint.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);

            String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",
                    Locale.getDefault()).format(new Date());

            int x = 20;
            int y = resizedBitmap.getHeight() - 50;
            canvas.drawText(timestamp, x, y, paint);

            FileOutputStream out = new FileOutputStream(imgFile);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            resizedBitmap.recycle();

        } catch (IOException e) {
            Log.e("MediaCreator", "ERROR some troubles overlaying photo", e);
        }
    }

    public File overlayDataTimeVideo(File vidFile) {
        return null;
    }

    private Bitmap compressPhoto(File imgFile) {
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

        int maxWidth = 1920;
        int maxHeight = 1080;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float ratio = Math.min((float) maxWidth / width, (float) maxHeight / height);
        Bitmap resizedBitmap = bitmap;

        if (ratio < 1.0f) {
            int newWidth = Math.round(width * ratio);
            int newHeight = Math.round(height * ratio);
            resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        }

        int rotationDegrees = getRotationDegrees(imgFile);
        if (rotationDegrees != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotationDegrees);
            resizedBitmap = Bitmap.createBitmap(resizedBitmap, 0, 0,
                    resizedBitmap.getWidth(), resizedBitmap.getHeight(), matrix, true);
        }

        Bitmap result = resizedBitmap.copy(Bitmap.Config.ARGB_8888, true);

        if (resizedBitmap != bitmap) {
            resizedBitmap.recycle();
        }
        bitmap.recycle();

        return result;
    }


    private int getRotationDegrees(File file) {
        try {
            ExifInterface exif = new ExifInterface(file.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            Log.e("MediaCreator", "ERROR some troubles rotating photo", e);
            return 0;
        }
    }

    private String makeDirName(String orderNumber) {
        return orderNumber.replace('/', '_');
    }

}
