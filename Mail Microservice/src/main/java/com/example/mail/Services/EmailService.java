package com.example.mail.Services;

import java.io.IOException;

import javax.mail.MessagingException;

import com.example.mail.Entity.EmailEntity;

public interface EmailService {
  void sendOrderReceipt(EmailEntity emailEntity, String attachmentPath) throws MessagingException, IOException;

}
