package com.example.Catalog.feign;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ecommerce",url = "http://10.20.3.153:8780/user/")
public interface FeignInterface
{
    @GetMapping(value = "getUserName/{email}")
    String getUserName(@PathVariable("email") String userId);
}
