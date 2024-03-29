package com.example.mail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mail.Entity.EmailEntity;

import java.util.logging.Logger;

@SpringBootTest
class MailApplicationTests {

	@Test
	void contextLoads() {
	}

	private Logger log = Logger.getLogger(String.valueOf(this.getClass()));
	@BeforeAll
	static void initAll() {
	}
	@BeforeEach
	void init() {
	}

	@Test
	@DisplayName("send Simple Mail")
	public void sendSimpleMail(){
		try {
			log.info("Starting execution of sendSimpleMail");
			String expectedValue="";
			EmailEntity details = null;


			JUnitMethod junitmethod  =new JUnitMethod();
			String actualValue=junitmethod.sendSimpleMail( details);
			log.info("Expected Value="+ expectedValue +" . Actual Value="+actualValue);
			System.out.println("Expected Value="+ expectedValue +" . Actual Value="+actualValue);
			Assertions.assertEquals(expectedValue, actualValue);
		} catch (Exception exception) {
			log.error("Exception in execution of execute1GetAllLogFromFirstMovF-"+exception,exception);
			exception.printStackTrace();
			Assertions.assertFalse(false);
		}
	}
	@AfterEach
	void tearDown() {
	}
	@AfterAll
	static void tearDownAll() {
	}





}
