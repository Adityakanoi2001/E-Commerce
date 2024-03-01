package com.example.CartEcommerce.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmailService {

    private String recipient;
    private String msgBody;
    private String subject;
}
