package org.example.watcher;

import org.example.config.AppConfig;
import org.example.service.EmailService;
import org.example.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class DirectoryWatcher {
    private final FileService fileService;
    private final EmailService emailService;

    public DirectoryWatcher(FileService fileService, EmailService emailService) {
        this.fileService = fileService;
        this.emailService = emailService;
    }

    public void startWatching() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(AppConfig.WATCH_DIRECTORY);

            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY
            );

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    handleWatchEvent(event);
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("An error occurred: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }

    private void handleWatchEvent(WatchEvent<?> event) {
        try {
            if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                processFileCreation();
            }
        } catch (Exception e) {
            System.err.println("Error processing event: " + e.getMessage());
        }
    }

    private void processFileCreation() throws Exception {
        // Send email with attachment
        emailService.sendFileNotification(AppConfig.WATCH_DIRECTORY);

        // Wait before deletion
        Thread.sleep(5000);

        // Delete the file
        String lastAddedFile = fileService.getLastAddedFileName(AppConfig.WATCH_DIRECTORY);
        if (!lastAddedFile.isEmpty()) {
            boolean deleted = fileService.deleteFile(AppConfig.WATCH_DIRECTORY + File.separator + lastAddedFile);
            if (deleted) {
                System.out.println("File: " + lastAddedFile + " has been deleted from " + AppConfig.WATCH_DIRECTORY);
                System.out.println("_________________________________________________________________________________________");
            }
        }
    }
}