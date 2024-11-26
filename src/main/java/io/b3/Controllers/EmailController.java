package io.b3.Controllers;

import io.b3.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-mail")
    public String sendMail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body) {
        emailService.sendEmail(to, subject, body);
        return "Email sent successfully!";
    }
}
