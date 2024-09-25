package com.pms.pms.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    //@Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendEmailWithToken(String userEmail, String link) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage , "utf-8");
         String subject = "Join Project Team Invitaion";
         String text = "Click the link to join project team " + link ;
         helper.setSubject(subject);
         helper.setText(text, true);
         helper.setTo(userEmail);

         try{
             mailSender.send(mimeMessage);
         } catch (MailSendException e) {
             throw new MailSendException("Failed to send email", e);
         }
    }
}
