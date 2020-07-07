package com.example.heroku;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductTests {

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUri() {
		String uri = template.getRootUri();
		baseURI = uri + "/product";
	}

	@Test
	public void allAvailables() {
		given().header("user-token", "4310cbb6711081156c5d6145d745d711").when().get("/available").then().statusCode(200);
	}
	
	@Test
	public void delete() {
		given().header("user-token", "4310cbb6711081156c5d6145d745d711").when().delete("/1000").then().statusCode(200);
	}

	@Test
	public void save() {
		String json = ""
				+ "{\n" + 
				"    \"name\": \"Nova blusa teste\",\n" + 
				"    \"productDesc\": \"Blusa da hora\",\n" + 
				"    \"price\": 121.50,\n" + 
				"    \"stock\": 20,\n" + 
				"    \"enable\": true\n" + 
				"}";
		
		given().contentType(ContentType.JSON).body(json).header("user-token", "4310cbb6711081156c5d6145d745d711")
				.when().put("/").then().statusCode(201);
	}
	

	@Test
	public void update() {
		String json = ""
				+ "{\n" + 
				"    \"id\": 3000,\n" + 
				"    \"name\": \"Nova blusa 2.5\",\n" + 
				"    \"productDesc\": \"Blusa da hora\",\n" + 
				"    \"price\": 121.50,\n" + 
				"    \"stock\": 20,\n" + 
				"    \"enable\": true\n" + 
				"}";
		
		given().contentType(ContentType.JSON).body(json).header("user-token", "4310cbb6711081156c5d6145d745d711")
				.when().post("/").then().statusCode(201);
	}

}
