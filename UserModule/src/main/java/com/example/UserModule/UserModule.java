package com.example.UserModule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.UserModule.properties.ApplicationProperties;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableConfigurationProperties({ApplicationProperties.class})
public class UserModule {

  public static void main(String[] args) {
    SpringApplication.run(UserModule.class, args);
  }

}
