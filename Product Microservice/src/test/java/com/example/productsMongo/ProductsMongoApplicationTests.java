package com.example.productsMongo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.example.productsMongo.entities.ProductsEntity;
import com.example.productsMongo.entities.Reviews;
import com.example.productsMongo.repositories.ProductsRepo;
import com.example.productsMongo.services.ProductsService;
import com.example.productsMongo.services.impl.ProductsServiceImpl;

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









