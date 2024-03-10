package com.example.mail.Services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.mail.MessagingException;

import com.example.mail.Entity.EmailEntity;
import com.example.mail.Entity.InvoiceDetailEntity;

public interface EmailService {
  void sendOrderReceipt(EmailEntity emailEntity, String attachmentPath) throws MessagingException, IOException;

  ByteArrayOutputStream generateByteArray(InvoiceDetailEntity invoice);
}
