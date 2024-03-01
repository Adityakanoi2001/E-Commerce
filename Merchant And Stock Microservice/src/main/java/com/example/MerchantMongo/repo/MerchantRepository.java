package com.example.MerchantMongo.repo;



import com.example.MerchantMongo.entity.Merchant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends MongoRepository<Merchant,String>
{
    public boolean existsByMerchantIdAndMerchantPassword(String merchantId,String merchantPassword);
}
