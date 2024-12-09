package io.b3.Services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            message.setTo(to);
            helper.setTo(to);
            message.setSubject(subject);
            helper.setSubject(subject);
            message.setText(body);
            helper.setText("<h1>Welcome</h1><p>This is an <b>HTML</b> email.</p>", true);
            message.setFrom("pavithran.prabhu47@gmail.com");  // Use your Gmail email here
            helper.setFrom("pavithran.prabhu47@gmail.com");
            mailSender.send(mimeMessage);
            mailSender.send(message);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
