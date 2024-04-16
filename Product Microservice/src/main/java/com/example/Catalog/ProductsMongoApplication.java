package com.example.Catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@EnableFeignClients
public class ProductsMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsMongoApplication.class, args);
	}

}
