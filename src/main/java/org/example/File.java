package org.example;

import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

// Using JavaMail API library
public class File {

//    public boolean checkFileOrFolder(String path){
//        java.io.File folder = new java.io.File(path);
//        java.io.File[] listOfFiles = folder.listFiles();
//        for (String element : listOfFiles.){
//
//        }
//    }

    public String getLastAddedFileName(String path) {
        java.io.File folder = new java.io.File(path);
        java.io.File[] listOfFiles = folder.listFiles();
//        System.out.println(Arrays.toString(listOfFiles));
        if (listOfFiles != null && listOfFiles.length > 0) {
            Arrays.sort(listOfFiles, Comparator.comparingLong(java.io.File::lastModified));
            java.io.File lastAddedFile = listOfFiles[listOfFiles.length - 1];
            if(lastAddedFile.isFile()) {
                return lastAddedFile.getName();
            }
            else {
                return "";
            }
        }
        return "";
    }

    public void sendEmail() {
        String toEmail = "test@hotmail.com";//change accordingly
        String fromEmail = "test@gmail.com";
        final String username = "changeaccordingly";//change accordingly
        final String password = "changeaccordingly";//change accordingly

        //1) get the session object
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        //2) compose message - start our mail message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("File Added");

            Multipart emailContent = new MimeMultipart();

            //Text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("A file is added within listening directory");

            //Attachment body part.
            MimeBodyPart attachment = new MimeBodyPart();

            String nameOfLastFile = getLastAddedFileName("C:\\Users\\testUser\\Desktop\\test");
            attachment.attachFile("C:\\Users\\testUser\\Desktop\\test\\" + nameOfLastFile);

            //Attach body parts
            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(attachment);

            //Attach multipart to message
            message.setContent(emailContent);

            Transport.send(message);
            System.out.println("Message was sent");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}


