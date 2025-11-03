package com.example.UserModule.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.UserModule.properties.ApplicationProperties;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;


@Configuration
public class SwaggerConfig {
  @Autowired
  ApplicationProperties applicationProperties;

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().servers(List.of(new Server().url("http://localhost:8800")
            .description("Local Development Server")))
        .info(new Info().title("BliCommerce User Authentication Microservice")
            .version(applicationProperties.getVersion() != null ? applicationProperties.getVersion() : "0.0.1-SNAPSHOT")
            .description("API's for Sign-In and Sign-Up for the User")
            .contact(new Contact().name("Aditya Kanoi")
                .url("https://adityakanoi123.wixsite.com/adityakanoi")
                .email("adityakanoi123@gmail.com"))
            .license(new License().name("Apache License Version 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0")));
  }
}

