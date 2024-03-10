package com.example.mail.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;

import com.example.mail.DTO.EmailDetailsDTO;
import com.example.mail.DTO.InvoiceDetailDTO;
import com.example.mail.Entity.InvoiceDetailEntity;
import com.example.mail.Helper.Constants;
import com.example.mail.Helper.MailMicroserviceApiPath;
import com.example.mail.Services.EmailService;
import com.example.mail.Entity.EmailEntity;

import io.swagger.annotations.Api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(Constants.CONTROLLER_HEADING)
@RequestMapping(MailMicroserviceApiPath.BASE_PATH)
@RestController
public class MailController {

  @Autowired
  EmailService sendMailService;

  private static final String ATTACHMENT_PATH = "src/main/resources/banner.txt";

  @Operation(description = "API ENDPOINT TO SEND MAIL")
  @PostMapping(MailMicroserviceApiPath.SEND_MAIL)
  public ResponseEntity<String> sendEmailForOrder(@RequestBody EmailDetailsDTO emailDetailsDTO) {
    try {
      log.info("Invoking API to Send Mail with Bill Receipt at Time {} and to User {}",
          new Date(),
          emailDetailsDTO.getCustomerEmail());
      if (emailDetailsDTO == null || emailDetailsDTO.getCustomerEmail() == null) {
        return new ResponseEntity<>("Invalid Input Data", HttpStatus.BAD_REQUEST);
      }
      EmailEntity emailEntity = new EmailEntity();
      BeanUtils.copyProperties(emailDetailsDTO, emailEntity);
      try {
        sendMailService.sendOrderReceipt(emailEntity, ATTACHMENT_PATH);
        return new ResponseEntity<>("Order Mail Sent Successfully", HttpStatus.OK);
      } catch (IOException | MessagingException e) {
        log.error("Error sending email: {}", e.getMessage());
        return new ResponseEntity<>("Failed to send email", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } catch (Exception e) {
      log.error("Unexpected error: {}", e.getMessage());
      return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(MailMicroserviceApiPath.PDF_BILL_GENERATION)
  public ResponseEntity<byte[]> generatePdf(@RequestBody InvoiceDetailDTO invoiceDetailDTO) {
    if (invoiceDetailDTO == null || invoiceDetailDTO.getItems() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    InvoiceDetailEntity invoiceDetailEntity = new InvoiceDetailEntity();
    BeanUtils.copyProperties(invoiceDetailDTO, invoiceDetailEntity);
    ByteArrayOutputStream outputStream = this.sendMailService.generateByteArray(invoiceDetailEntity);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(ContentDisposition.builder("attachment")
        .filename(invoiceDetailDTO.getBuyer() + "-" + "invoice.pdf")
        .build());
    return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
  }
}
