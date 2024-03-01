package com.example.ecommerce.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Getter
@Setter
public class DeatilsDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String city;
    private String state;
}
