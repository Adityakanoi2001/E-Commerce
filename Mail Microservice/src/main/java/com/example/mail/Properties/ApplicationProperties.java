package com.example.mail.Properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(value = "application")
public class ApplicationProperties {
  private boolean production = true;
  private String version;
}

