package com.example.ecommerce.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignInDto {
    private String email;
    private String password;

}