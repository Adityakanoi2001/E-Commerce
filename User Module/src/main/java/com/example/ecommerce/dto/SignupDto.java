package com.example.ecommerce.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignupDto
{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String city;
    private String state;
}