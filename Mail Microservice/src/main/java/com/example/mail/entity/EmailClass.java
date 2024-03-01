package com.example.mail.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class EmailClass {

    private String recipient ;
    private String msgBody;
    private String subject = "Order Detail";
}