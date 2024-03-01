package com.example.MerchantMongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@EnableFeignClients
@SpringBootApplication
public class MerchantMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchantMongoApplication.class, args);
	}

}
