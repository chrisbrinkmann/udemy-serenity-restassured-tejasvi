package com.udemy.tejasvi.studentapp.junit;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Pending;

@RunWith(SerenityRunner.class)
public class FirstSerenityTest {
	
	@BeforeClass
	public static void init() {
		System.out.println("Initializing Test");
		RestAssured.baseURI="http://localhost:8080";
	}

	@Test
	public void getAllStudents() {
		SerenityRest
			.given()
			.when()
				.get("/student/list")
			.then()
				.log()
				.all()
				.statusCode(200);
	}
	
	@Test
	public void failTest() {
		SerenityRest
			.given()
			.when()
				.get("/student/list")
			.then()
				.log()
				.all()
				.statusCode(500);
	}
	
	@Pending
	@Test
	public void pendingTest() {
	}
	
	@Ignore
	@Test
	public void skippedTest() {
	}
	

	@Test
	public void errorTest() {
		System.out.println("This is an error" + (5/0));
	}
}
