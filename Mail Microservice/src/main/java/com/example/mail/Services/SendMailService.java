package com.example.mail.Services;


import com.example.mail.entity.EmailClass;

public interface SendMailService {
        String sendSimpleMail(EmailClass details);
}
