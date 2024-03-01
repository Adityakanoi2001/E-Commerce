package com.example.MerchantMongo.controller;



import com.example.MerchantMongo.dto.*;
import com.example.MerchantMongo.entity.Merchant;
import com.example.MerchantMongo.entity.ProductEntity;
import com.example.MerchantMongo.feign.FeignInterface;
import com.example.MerchantMongo.repo.MerchantRepository;
import com.example.MerchantMongo.repo.ProductRep;
import com.example.MerchantMongo.services.MerchantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/merchant")
public class MerchantController
{
    @Autowired
    MerchantService merchantService;

@Autowired
FeignInterface feignInterface;

    @Autowired
    ProductRep productRep;

    @PostMapping("/addMerchant")
    public ResponseEntity<SignUpValidationStatus> addMerchant (@RequestBody MerchantDTO merchantDto)
    {
        SignUpValidationStatus signUpValidationStatus = new SignUpValidationStatus();
        Merchant merchant1 = new Merchant();
        BeanUtils.copyProperties(merchantDto,merchant1);
        merchantService.insertOrUpdate(merchant1);
        signUpValidationStatus.setStatus(true);
        return new ResponseEntity(signUpValidationStatus,HttpStatus.CREATED);
    }

    @GetMapping("/getMerchnatbyId/{merchantId}")
    public Optional getMerchant (@RequestParam String merchantId)
    {
        return merchantService.findByMerchantId(merchantId);
    }

    @GetMapping("/getauthentication/{merchnatId,merchnatPassword")
    public ResponseEntity<ValidationStatus> signin (@RequestBody MerhchantSignInDTO merhchantSignInDTO)
    {
        ValidationStatus validationStatus = new ValidationStatus();
        if(merchantService.existsByMerchantIdAndMerchantPassword(merhchantSignInDTO.getMerchantId(),merhchantSignInDTO.getMerchantPassword()))
        {
            validationStatus.setStatus(true);
        }
        else
        {
            validationStatus.setStatus(false);
        }
        return new ResponseEntity(validationStatus,HttpStatus.OK);
    }

}
