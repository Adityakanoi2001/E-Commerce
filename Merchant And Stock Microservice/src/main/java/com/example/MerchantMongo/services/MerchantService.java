package com.example.MerchantMongo.services;

import com.example.MerchantMongo.entity.Merchant;


import java.util.Optional;

public interface MerchantService {

    public Merchant insertOrUpdate(Merchant merchant);
    public Optional findByMerchantId(String merchantId);
    public boolean existsByMerchantIdAndMerchantPassword (String merchantId,String merchantPassword);

}
