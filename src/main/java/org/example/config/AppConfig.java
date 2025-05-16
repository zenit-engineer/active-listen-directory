package org.example.config;

public class AppConfig {
    public static final String WATCH_DIRECTORY = System.getenv("WATCH_DIRECTORY");
    public static final String SMTP_HOST = System.getenv("SMTP_HOST");
    public static final String SMTP_PORT = System.getenv("SMTP_PORT");
    public static final String SMTP_USERNAME = System.getenv("SMTP_USERNAME");
    public static final String SMTP_PASSWORD = System.getenv("SMTP_PASSWORD");
    public static final String FROM_EMAIL = System.getenv("FROM_EMAIL");
    public static final String TO_EMAIL = System.getenv("TO_EMAIL");

    private AppConfig() {} // Prevent instantiation
}