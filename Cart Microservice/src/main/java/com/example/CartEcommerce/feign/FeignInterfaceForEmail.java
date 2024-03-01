package com.example.CartEcommerce.feign;


import com.example.CartEcommerce.dto.EmailService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mail",url = "http://10.20.3.153:8767/mails/")
public interface FeignInterfaceForEmail {

    @PostMapping("sendMail")
    public  String callEmailServer(@RequestBody EmailService emailService);
}
