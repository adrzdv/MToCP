package com.adrzdv.mtocp.util;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZipUtil {

    public static void zipFolfer(File folder, File zipFile) throws IOException {
        ZipFile zip = new ZipFile(zipFile);
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);

        List<File> files = getFilesInFolder(folder);
        for (File file : files) {
            ZipParameters fileParam = new ZipParameters();
            fileParam.setCompressionMethod(CompressionMethod.DEFLATE);
            fileParam.setCompressionLevel(CompressionLevel.HIGHER);
            fileParam.setFileNameInZip(folder.toURI().relativize(file.toURI()).getPath());
            zip.addFile(file, fileParam);
        }

    }

    private static List<File> getFilesInFolder(File folder) {
        List<File> fileList = new ArrayList<>();
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    fileList.addAll(getFilesInFolder(file));
                } else {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }
}
