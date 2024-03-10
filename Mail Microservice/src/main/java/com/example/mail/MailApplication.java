package com.example.mail;

import com.example.mail.Properties.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableConfigurationProperties({ApplicationProperties.class})
public class MailApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(MailApplication.class, args);
		System.out.println("The Server Started @ http://localhost:8000/swagger-ui/index.html#/");
	}

}
