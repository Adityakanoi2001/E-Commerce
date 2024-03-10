package com.example.mail.Services.ServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.mail.Entity.InvoiceDetailEntity;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Div;
import lombok.extern.slf4j.Slf4j;
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

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.source.ByteArrayOutputStream;

import java.util.Arrays;
import java.util.List;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.properties.TextAlignment;

@Slf4j
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
    context.setVariable("discount", emailEntity.getDiscount());
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
    context.setVariable("phoneNumber", emailEntity.getCustomerPhoneNumber());
    context.setVariable("pickupMapLink",
        "https://www.google.com/maps/search/?api=1&query=" + emailEntity.getPickupAddress());
    context.setVariable("dropMapLink",
        "https://www.google.com/maps/search/?api=1&query=" + emailEntity.getDropAddress());

    String body = templateEngine.process("MailTemplate", context);
    helper.setTo(emailEntity.getCustomerEmail());
    helper.setSubject(
        "Receipt for Your Order " + emailEntity.getInvoiceNumber() + " " + emailEntity.getConsigneeName());
    helper.setText(body, true);
    if (attachmentPath != null && !attachmentPath.isEmpty()) {
      Path attachmentFilePath = Paths.get(attachmentPath);
      byte[] attachmentBytes = Files.readAllBytes(attachmentFilePath);
      ByteArrayResource resource = new ByteArrayResource(attachmentBytes);
      helper.addAttachment(attachmentFilePath.getFileName().toString(), resource);
    }
    mailSender.send(message);
  }

  public ByteArrayOutputStream generateByteArray(InvoiceDetailEntity invoice) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      PdfWriter writer = new PdfWriter(outputStream);
      PdfDocument pdfDoc = new PdfDocument(writer);
      PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
      Document doc = new Document(pdfDoc).setFont(font);

      // Add a header with a small logo in the left top corner and title
      ImageData logoData = ImageDataFactory.create("images/BKLogo.png");
      Image logo = new Image(logoData).setHeight(75).setWidth(75); // Adjust the height as needed
      Paragraph title = new Paragraph("BOMBAY KITCHEN").setFont(font).setFontSize(24).setFontColor(ColorConstants.RED);

      // Set up a header section with logo, title, and tagline
      Div headerDiv = new Div();
      headerDiv.add(logo);
      headerDiv.add(new Paragraph("\u00a0")); // Add a non-breaking space for spacing
      headerDiv.add(title);
      doc.add(headerDiv);

      // Add an empty line for spacing
      doc.add(new Paragraph(" "));

      // Add a tagline
      Paragraph tagline =
          new Paragraph("Where Flavour meets Food").setFont(font).setFontSize(14).setFontColor(ColorConstants.GRAY);
      doc.add(tagline);

      // Add an empty line for spacing
      doc.add(new Paragraph(" "));

      // Add a title for the invoice
      Paragraph invoiceText =
          new Paragraph("Order Invoice").setFont(font).setFontSize(20).setFontColor(ColorConstants.BLACK);
      doc.add(invoiceText);

      // Add an empty line for spacing
      doc.add(new Paragraph(" "));

      // Set up the header table with seller and buyer information
      float[] pointColumnWidths = {280F, 280F};
      Table header = new Table(pointColumnWidths);
      String seller = "Seller:\n" + invoice.getSeller() + "\n" + invoice.getSellerAddress();
      header.addCell(new Cell().add(new Paragraph(seller)).setPadding(10));
      String buyer = "Buyer:\n" + invoice.getBuyer() + "\n" + invoice.getBuyerAddress() + "\n Phone Number: "
          + invoice.getBuyersPhoneNumber();
      header.addCell(new Cell().add(new Paragraph(buyer)).setPadding(10));
      doc.add(header);

      // Add a table for product information
      float[] productInfoColumnWidths = {200, 100, 100, 100}; // Adjusted column widths
      Table productInfoTable = new Table(productInfoColumnWidths);
      productInfoTable.setTextAlignment(TextAlignment.CENTER);
      productInfoTable.addCell(new Cell().add(new Paragraph("Item")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
      productInfoTable.addCell(new Cell().add(new Paragraph("Quantity")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
      productInfoTable.addCell(new Cell().add(new Paragraph("Rate")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
      productInfoTable.addCell(new Cell().add(new Paragraph("Amount")).setBackgroundColor(ColorConstants.LIGHT_GRAY));

      // Calculate the total amount properly
      float totalAmount = 0;
      List<String> items = Arrays.asList("Product A", "Product B"); // List of items from the invoice
      List<Integer> quantities = Arrays.asList(2, 1); // List of quantities from the invoice
      List<Float> rates = Arrays.asList(12.99f, 15.49f); // List of rates from the invoice

      for (int i = 0; i < items.size(); i++) {
        String item = items.get(i);
        int quantity = quantities.get(i);
        float rate = rates.get(i);
        float amount = quantity * rate;
        totalAmount += amount;

        productInfoTable.addCell(new Cell().add(new Paragraph(item)));
        productInfoTable.addCell(new Cell().add(new Paragraph(String.valueOf(quantity))));
        productInfoTable.addCell(new Cell().add(new Paragraph(String.valueOf(rate))));
        productInfoTable.addCell(new Cell().add(new Paragraph(String.valueOf(amount))));
      }

      // Add a new row with the overall total
      productInfoTable.addCell(new Cell(1, 3).add(new Paragraph("Overall Total:").setFontColor(ColorConstants.RED)));
      productInfoTable.addCell(new Cell().add(new Paragraph(String.valueOf(totalAmount)))
          .setBackgroundColor(ColorConstants.LIGHT_GRAY));
      doc.add(productInfoTable);

      // Add a signature section with small images
      ImageData smallSignatureData = ImageDataFactory.create("images/BKLogo.png");
      Image smallSignature = new Image(smallSignatureData).setHeight(75).setWidth(75); // Adjust the height as needed
      doc.add(smallSignature);
      doc.add(new Paragraph(" ")); // Add a space between small signatures

      ImageData signatureData = ImageDataFactory.create("images/BKLogo.png");
      Image signature = new Image(signatureData).setHeight(40).setWidth(40); // Adjust the height as needed
      Paragraph signatureText =
          new Paragraph("Bombay Kitchen").setFont(font).setFontSize(12).setFontColor(ColorConstants.BLACK);

      doc.add(signature);
      doc.add(signatureText);

      // Add a thanks message
      Paragraph thanksMessage = new Paragraph("THANKS FOR ORDERING FROM BOMBAY KITCHEN").setFont(font)
          .setFontSize(12)
          .setFontColor(ColorConstants.GRAY);
      doc.add(thanksMessage);

      writer.close();
      pdfDoc.close();
      doc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    log.info("PDF Generated Successfully...");
    return outputStream;
  }
}
