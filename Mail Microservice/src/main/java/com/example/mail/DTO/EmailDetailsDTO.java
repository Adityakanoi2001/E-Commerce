package com.example.mail.DTO;

import lombok.Data;

@Data
public class EmailDetailsDTO {
  private String consignorName;
  private String consigneeName;
  private String goodsNature;
  private String invoiceNumber;
  private String invoiceDate;
  private double totalAmount;
  private double deliveryCharges;
  private double discount;
  private double subTotal;
  private double netFare;
  private String deliveryPartnerName;
  private String pickupTime;
  private String pickupDate;
  private String pickupAddress;
  private String dropTime;
  private String dropDate;
  private String dropAddress;
  private String customerPhoneNumber;
  private String customerEmail;
}
