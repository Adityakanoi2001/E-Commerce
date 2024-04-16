package com.example.Catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.example.Catalog"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title("BliCommerce Product Catalog")
            .description("API's for Product Catalog for E-Commerce")
            .contact(new Contact("Aditya Kanoi", "https://adityakanoi123.wixsite.com/adityakanoi", "adityakanoi123@gmail.com"))
            .license("Apache License Version 2.0")
            .build();
    }
}
