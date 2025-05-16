package org.example;

import org.example.service.EmailService;
import org.example.service.FileService;
import org.example.watcher.DirectoryWatcher;

public class Main {
    public static void main(String[] args) {
        // Initialize services
        FileService fileService = new FileService();
        EmailService emailService = new EmailService(fileService);

        // Start directory watcher
        DirectoryWatcher watcher = new DirectoryWatcher(fileService, emailService);
        watcher.startWatching();
    }
}