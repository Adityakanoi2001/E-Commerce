package com.example.mail.Entity;

import com.example.mail.DTO.InvoiceDetailDTO;
import com.example.mail.DTO.ItemDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailEntity {
  private String seller;
  private String sellerAddress;
  private String buyer;
  private String buyerAddress;
  private String buyersPhoneNumber;
  private List<ItemDetailEntity> items;
}
