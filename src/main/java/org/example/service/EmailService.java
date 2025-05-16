package org.example.service;

import org.example.config.AppConfig;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailService {
    private final FileService fileService;

    public EmailService(FileService fileService) {
        this.fileService = fileService;
    }

    public void sendFileNotification(String directoryPath) throws MessagingException, IOException {
        String lastAddedFileName = fileService.getLastAddedFileName(directoryPath);
        if (lastAddedFileName.isEmpty()) {
            return;
        }

        // Email setup
        Properties properties = new Properties();
        properties.put("mail.smtp.host", AppConfig.SMTP_HOST);
        properties.put("mail.smtp.port", AppConfig.SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AppConfig.SMTP_USERNAME, AppConfig.SMTP_PASSWORD);
            }
        });

        // Compose message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(AppConfig.FROM_EMAIL));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(AppConfig.TO_EMAIL));
        message.setSubject("File Added");

        Multipart emailContent = new MimeMultipart();

        // Text body part
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText("A file is added within listening directory");

        // Attachment body part
        MimeBodyPart attachment = new MimeBodyPart();
        attachment.attachFile(directoryPath + File.separator + lastAddedFileName);

        // Attach body parts
        emailContent.addBodyPart(textBodyPart);
        emailContent.addBodyPart(attachment);

        // Attach multipart to message
        message.setContent(emailContent);

        Transport.send(message);
        System.out.println("Message was sent");
    }
}