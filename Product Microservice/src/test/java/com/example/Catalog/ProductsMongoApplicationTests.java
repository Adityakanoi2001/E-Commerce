package com.example.Catalog;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.Assert.assertEquals;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.Catalog.repositories.ProductsRepo;
import com.example.Catalog.services.impl.ProductsServiceImpl;

@SpringBootTest
class ProductsMongoApplicationTests {

	@Test
	void contextLoads() {
	}


	@Mock
	private MongoTemplate mongoTemplate;

	@Mock
	ProductsRepo productsRepo;

	@InjectMocks
	ProductsServiceImpl productsService;


	
}









