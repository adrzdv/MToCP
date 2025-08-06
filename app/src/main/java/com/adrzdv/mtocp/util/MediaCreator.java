package com.adrzdv.mtocp.util;

import static com.adrzdv.mtocp.util.DirectoryHandler.MEDIA_DIRECTORY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
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

    public File getPhotoFile(String orderNumber, String coachNumber, int violationCode) {
        mediaFile = createPhotoFile(orderNumber, coachNumber, IMG_PREFIX + violationCode, IMG_EXT);
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
        String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss", Locale.getDefault()).format(new Date());
        return new File(mediaDir, prefix + "_" + timestamp + extension);
    }

    public void overlayDataTime(File imgFile) {
        if (imgFile == null || !imgFile.exists()) {
            Log.e("MediaCreator", "File not found!");
            return;
        }

        try {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            Canvas canvas = new Canvas(mutableBitmap);
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            paint.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);

            String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",
                    Locale.getDefault()).format(new Date());

            int x = 20;
            int y = mutableBitmap.getHeight() - 50;
            canvas.drawText(timestamp, x, y, paint);

            FileOutputStream out = new FileOutputStream(imgFile);
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (IOException e) {
            Log.e("MediaCreator", "ERROR some troubles overlaying photo", e);
        }
    }

    private String makeDirName(String orderNumber) {
        return orderNumber.replace('/', '_');
    }

}
