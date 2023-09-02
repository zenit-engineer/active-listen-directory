package org.example;

import java.io.IOException;
import java.nio.file.*;

//We are watching specific folder for events like update,create and delete using JAVA NIO(is an alternative IO API for Java).
//We are seting up a file watcher using the WatchService API introduced in Java 7.
//This service monitors a specified directory for changes to its files (create, delete, or modify) and prints out the corresponding events.
public class DirectoryListener extends File {
    public static void main(String[] args){
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();  // WatchService setup
            /*Make sure the directory you specify exists and that the program has appropriate permissions to access it.*/
            Path path = Paths.get("C:\\Users\\neziri.zenit\\Desktop\\test"); // Set up the folder path u want to monitor
            // Register directory with the WatchService instance and specify events to be monitored
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE
//                    StandardWatchEventKinds.ENTRY_DELETE
//                    StandardWatchEventKinds.ENTRY_MODIFY
            );
            WatchKey key;
            File file = new File();
            // Watch for events indefinitely
            while ((key = watchService.take()) != null) {
                // Process all events for the key
                for (WatchEvent<?> event : key.pollEvents()) {
                    file.sendEmail();
                    try {
                        // Pause the execution for 1 minute (60 seconds)
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    java.io.File f = new java.io.File("C:\\Users\\neziri.zenit\\Desktop\\test\\" +
                            file.getLastAddedFileName("C:\\Users\\neziri.zenit\\Desktop\\test"));
                    String deletedFile = file.getLastAddedFileName("C:\\Users\\neziri.zenit\\Desktop\\test");
                    f.delete();
                    System.out.println("File: " + deletedFile + " has been deleted from C:\\Users\\neziri.zenit\\Desktop\\test");
                    System.out.println("_________________________________________________________________________________________");
                }
                // Reset the key to receive further events
                key.reset();
            }
        }
//        IOException may be thrown if there are any issues with the file operations or accessing the file system,
//        while InterruptedException is thrown if the thread executing the code is interrupted while waiting for events in the WatchService.
        catch (IOException | InterruptedException e){
            // Handle exceptions here
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}