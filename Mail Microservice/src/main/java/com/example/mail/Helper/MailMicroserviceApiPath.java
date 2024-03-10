package com.example.mail.Helper;

public interface MailMicroserviceApiPath {
  String BASE_PATH = Constants.CONTEXT_PATH + "/" + "MailMicroserviceController";
  String SEND_MAIL = "/sendEmailForOrder";
  String PDF_BILL_GENERATION = "/generatePdf";
}
