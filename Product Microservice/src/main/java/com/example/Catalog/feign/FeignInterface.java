package com.example.Catalog.feign;



import com.example.Catalog.dto.ExternalMerchantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ecommerce",url = "http://10.20.3.153:8780/user/")
public interface FeignInterface
{
    @GetMapping(value = "getUserName/{email}")
    String getUserName(@PathVariable("email") String userId);

    @GetMapping(value = "")
    List<ExternalMerchantDto> getMerchantDetailList(List<String> merchantIds);
}
