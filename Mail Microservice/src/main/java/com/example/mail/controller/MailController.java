package com.example.mail.controller;


import com.example.mail.Services.SendMailService;
import com.example.mail.entity.EmailClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/mails")
@RestController
public class MailController {

    @Autowired
    SendMailService sendMailService;

@PostMapping("/sendMail")
public String sendMail(@RequestBody EmailClass details) {
    String status = sendMailService.sendSimpleMail(details);
    return status;
}

}
