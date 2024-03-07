package com.example.mail.Services.ServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.example.mail.Services.EmailService;
import com.example.mail.Entity.EmailEntity;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private SpringTemplateEngine templateEngine;

  @Override
  public void sendOrderReceipt(EmailEntity emailEntity, String attachmentPath) throws MessagingException, IOException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariable("consignorName", emailEntity.getConsignorName());
        context.setVariable("consigneeName", emailEntity.getConsigneeName());
        context.setVariable("goodsNature", emailEntity.getGoodsNature());
        context.setVariable("invoiceNumber", emailEntity.getInvoiceNumber());
        context.setVariable("invoiceDate", emailEntity.getInvoiceDate());
        context.setVariable("discount",emailEntity.getDiscount());
        context.setVariable("totalAmount", emailEntity.getTotalAmount());
        context.setVariable("deliverCharges", emailEntity.getDeliveryCharges());
        context.setVariable("subTotal", emailEntity.getSubTotal());
        context.setVariable("netFare", emailEntity.getNetFare());
        context.setVariable("deliveryPartnerName", emailEntity.getDeliveryPartnerName());
        context.setVariable("pickupTime", emailEntity.getPickupTime());
        context.setVariable("pickupDate", emailEntity.getPickupDate());
        context.setVariable("pickupAddress", emailEntity.getPickupAddress());
        context.setVariable("dropTime", emailEntity.getDropTime());
        context.setVariable("dropDate", emailEntity.getDropDate());
        context.setVariable("dropAddress", emailEntity.getDropAddress());
        context.setVariable("phoneNumber",emailEntity.getCustomerPhoneNumber());
        context.setVariable("pickupMapLink",
            "https://www.google.com/maps/search/?api=1&query=" + emailEntity.getPickupAddress());
        context.setVariable("dropMapLink",
            "https://www.google.com/maps/search/?api=1&query=" + emailEntity.getDropAddress());

        String body = templateEngine.process("MailTemplate", context);
        helper.setTo(emailEntity.getCustomerEmail());
        helper.setSubject("Receipt for Your Order " + emailEntity.getInvoiceNumber() + " " + emailEntity.getConsigneeName());
        helper.setText(body, true);
        if (attachmentPath != null && !attachmentPath.isEmpty()) {
          Path attachmentFilePath = Paths.get(attachmentPath);
          byte[] attachmentBytes = Files.readAllBytes(attachmentFilePath);
          ByteArrayResource resource = new ByteArrayResource(attachmentBytes);
          helper.addAttachment(attachmentFilePath.getFileName().toString(), resource);
        }
    mailSender.send(message);
  }
}
