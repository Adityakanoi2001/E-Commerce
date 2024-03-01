package com.example.ecommerce.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignUpResponseDto {
    private String status;
    private String message;

    public SignUpResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
