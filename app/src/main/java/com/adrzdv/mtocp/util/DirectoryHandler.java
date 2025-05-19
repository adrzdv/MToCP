package com.adrzdv.mtocp.util;

import android.os.Environment;

import java.io.File;

public class DirectoryHandler {
    public static final String MEDIA_DIRECTORY = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) +
            "/mtocp/media";

    public static final String WORK_DIRECTORY = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) +
            "/mtocp";

    public static final String ZIP_DIRECTORY = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) +
            "/mtocp/zip";
    public static final String EXPORT_DIRECTORY = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) +
            "/mtocp/export";

    public static boolean cleanDirectories() {

        boolean res;
        File dir = new File(WORK_DIRECTORY);

        if (dir.exists()) {
            res = delRecursFile(dir);
            new File(MEDIA_DIRECTORY).mkdirs();
            new File(ZIP_DIRECTORY).mkdirs();
            new File(EXPORT_DIRECTORY).mkdirs();
        } else {
            res = false;
        }

        new File(WORK_DIRECTORY).mkdir();
        return res;

    }

    private static boolean delRecursFile(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                delRecursFile(child);
            }
        }
        return file.delete();
    }
}
