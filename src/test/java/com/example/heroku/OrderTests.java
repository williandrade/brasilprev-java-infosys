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
public class OrderTests {

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUri() {
		String uri = template.getRootUri();
		baseURI = uri + "/order";
	}

	@Test
	public void findAll() {
		given().header("user-token", "4310cbb6711081156c5d6145d745d711").when().get("/").then().statusCode(200);
	}

	@Test
	public void findById() {
		given().header("user-token", "4310cbb6711081156c5d6145d745d711").when().get("/{id}", 2000).then()
				.statusCode(200);
	}

	@Test
	public void findByClientId() {
		given().header("user-token", "4310cbb6711081156c5d6145d745d711").when().get("/client/{id}", 1000).then()
				.statusCode(200);
	}

	@Test
	public void makeAnOrder() {
		String json = ""
				+ "{\n" + 
				"    \"clientId\": 1000,\n" + 
				"    \"address\": \"Rua Jbs\",\n" + 
				"    \"products\": [\n" + 
				"        {\n" + 
				"            \"id\": 1000,\n" + 
				"            \"qtd\": 1,\n" + 
				"            \"obs\": \"Tamanho Grande\"\n" + 
				"        },\n" + 
				"        {\n" + 
				"            \"id\": 2000,\n" + 
				"            \"qtd\": 1,\n" + 
				"            \"obs\": \"Na cor azul\"\n" + 
				"        }\n" + 
				"    ]\n" + 
				"}";
		
		given().contentType(ContentType.JSON).body(json).header("user-token", "4310cbb6711081156c5d6145d745d711")
				.when().put("/").then().statusCode(201);
	}

}
