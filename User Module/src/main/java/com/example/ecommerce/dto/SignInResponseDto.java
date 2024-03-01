package com.example.ecommerce.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignInResponseDto {
    private String status;
    private String token;


    public SignInResponseDto(String status, String token) {
        this.status = status;
        this.token = token;
    }
}