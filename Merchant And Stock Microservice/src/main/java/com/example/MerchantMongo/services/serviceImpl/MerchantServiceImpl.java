package com.example.MerchantMongo.services.serviceImpl;


import com.example.MerchantMongo.entity.Merchant;
import com.example.MerchantMongo.repo.MerchantRepository;
import com.example.MerchantMongo.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MerchantServiceImpl implements MerchantService
{

    @Autowired
    MerchantRepository merchantRepository;

    @Override
    public Merchant insertOrUpdate(Merchant merchant)
    {

        return merchantRepository.save(merchant);
    }



    @Override
    public Optional<Merchant> findByMerchantId(String merchantId)
    {
        return merchantRepository.findById(merchantId);
    }

    @Override
    public boolean existsByMerchantIdAndMerchantPassword(String merchantId, String merchantPassword)
    {
        if (merchantRepository.existsByMerchantIdAndMerchantPassword(merchantId,merchantPassword))
        {
            return true;
        }
       return false;
    }


}
