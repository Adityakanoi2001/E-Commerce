package com.example.Catalog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ProductsCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsCatalogApplication.class, args);
		System.out.println("Server Started At" + " " +"http://localhost:8100/swagger-ui/index.html#/");
	}

}
