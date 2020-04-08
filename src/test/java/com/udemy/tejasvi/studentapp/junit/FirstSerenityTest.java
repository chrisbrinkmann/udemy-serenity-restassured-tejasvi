package com.udemy.tejasvi.studentapp.junit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;

@RunWith(SerenityRunner.class)
public class FirstSerenityTest {
	
	@BeforeClass
	public static void init() {
		System.out.println("Initializing Test");
		RestAssured.baseURI="http://localhost:8080";
	}

	@Test
	public void getAllStudents() {
		SerenityRest.given()
			.when()
			.get("/student/list")
			.then()
			.log()
			.all()
			.statusCode(200);
	}
}
