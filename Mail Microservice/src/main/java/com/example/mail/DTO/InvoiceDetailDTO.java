package com.example.mail.DTO;

import com.example.mail.Entity.ItemDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetailDTO {
  private String seller;
  private String sellerAddress;
  private String buyer;
  private String buyerAddress;
  private String buyersPhoneNumber;
  private List<ItemDetailEntity> items;
}
