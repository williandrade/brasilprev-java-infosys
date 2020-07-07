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
public class UserTests {

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUri() {
		String uri = template.getRootUri();
		baseURI = uri + "/user";
	}

	@Test
	public void update() {
		String json = ""
				+ "{\n" + 
				"    \"email\": \"test@teste.com\",\n" + 
				"    \"password\": \"teste\"\n" + 
				"}";
		
		given().contentType(ContentType.JSON).body(json).when().post("/login").then().statusCode(201);
	}

}
