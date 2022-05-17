package com.epam.delivery.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public final class EmailSender {
    private EmailSender() {
    }

    private static final String FROM = "dimakuian93@gmail.com";
    private static final String PASSWORD = "gpcmhofbatgsyoye";
    private static final Logger logger = LogManager.getLogger();

    /**
     * @param to email address recipient
     * @param subject email subject
     * @param text email text message
     */
    public static void sendMail(String to, String subject, String text) {
        logger.info("SendMail method start.");

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(FROM));
            logger.info("you send email from =>" + FROM);

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            logger.info("you send email to => " + to);

            // Set Subject: header field
            message.setSubject(subject);
            logger.info("message subject => " + FROM);

            // Now set the actual message
            message.setText(text);
            logger.info("message text => " + FROM);

           logger.info("sending...");
            // Send message
            Transport.send(message);
            logger.info("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
