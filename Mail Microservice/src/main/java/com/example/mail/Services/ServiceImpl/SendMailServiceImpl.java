package com.example.mail.Services.ServiceImpl;


import com.example.mail.Services.SendMailService;
import com.example.mail.entity.EmailClass;
import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
public class SendMailServiceImpl implements SendMailService

{
    @Autowired private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;

    public String sendSimpleMail(EmailClass details)
        {
            try
            {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(sender);
                mailMessage.setTo(details.getRecipient());
                mailMessage.setText(details.getMsgBody());
                mailMessage.setSubject(details.getSubject());
                javaMailSender.send(mailMessage);

                return "Mail Sent Successfully...";

            } catch (Exception e)
            {
                return "Error while Sending Mail";
            }
        }
}
