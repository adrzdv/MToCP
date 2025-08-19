package com.adrzdv.mtocp.domain.usecase;

import android.util.Log;

import com.adrzdv.mtocp.util.DirectoryHandler;
import com.adrzdv.mtocp.util.ZipUtil;

import java.io.File;

public class ArchivePhotoInZipUseCase {

    public File execute(String orderNumber) {
        String numberReplaced = orderNumber.replace('/', '_');
        File sourceFolder = new File(DirectoryHandler.MEDIA_DIRECTORY + "/" + numberReplaced);

        if (!sourceFolder.exists()) {
            Log.d("ZIPPER-USE-CASE", "Cant find source folder");
            return null;
        }

        File outputFile = new File(DirectoryHandler.ZIP_DIRECTORY + "/" + numberReplaced + ".zip");
        File zipFolder = new File(DirectoryHandler.ZIP_DIRECTORY);

        if (!zipFolder.exists()) {
            boolean isCreated = zipFolder.mkdirs();
            if (!isCreated) {
                Log.d("ZIPPER-USE-CASE", "Cant create output folder");
                return null;
            }
        }

        try {
            ZipUtil.zipFolfer(sourceFolder, outputFile);
        } catch (Exception e) {
            Log.d("ZIPPER-USE-CASE", "Runtime exception");
            return null;
        }

        return outputFile;
    }
}
