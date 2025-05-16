package org.example.service;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class FileService {

    public String getLastAddedFileName(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null && listOfFiles.length > 0) {
            Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified));
            File lastAddedFile = listOfFiles[listOfFiles.length - 1];
            if (lastAddedFile.isFile()) {
                return lastAddedFile.getName();
            }
        }
        return "";
    }

    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }
}