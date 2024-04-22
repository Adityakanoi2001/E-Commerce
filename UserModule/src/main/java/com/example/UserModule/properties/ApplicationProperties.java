package com.example.UserModule.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(value = "application")
public class ApplicationProperties {
  private boolean production = true;
  private String version;
}

