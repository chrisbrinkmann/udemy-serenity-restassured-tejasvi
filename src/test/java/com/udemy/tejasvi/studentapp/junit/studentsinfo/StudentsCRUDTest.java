package com.udemy.tejasvi.studentapp.junit.studentsinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.udemy.tejasvi.studentapp.model.StudentClass;
import com.udemy.tejasvi.studentapp.testbase.TestBase;
import com.udemy.tejasvi.studentapp.utils.Utils;

import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentsCRUDTest extends TestBase {
	
	static String firstName = "testUser" + Utils.getRandomIntString();
	static String lastName = "testUser" + Utils.getRandomIntString();
	static String programme = "ComputerScience";
	static String email = Utils.getRandomIntString() + "demouser@gmail.com";
	static HashMap<String, Object> studentMapCache;
	
	@Title("This test will create a new student")
	@Test
	public void aCreateStudent() {
		List<String> courses = new ArrayList<String>();
		courses.add("Java");
		courses.add("C++");
		
		// initialize POJO for create request
		StudentClass student = new StudentClass();
		student.setCourses(courses);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);
		
		// POST /student create student & check status code
		SerenityRest
			.rest()
			.given()
			.log()
			.all()
			.contentType(ContentType.JSON)
			.when()
			.body(student)
			.post()
			.then()
			.log()
			.all()
			.statusCode(201);
	}
	
	@Title("This test will verify a the student in the previous test was added")
	@Test
	public void bGetStudent() {
		
		// GET /list extract student & cache
		studentMapCache = SerenityRest
			.rest()
			.given()
			.when()
			.get("/list")
			.then()
			.log()
			.all()
			.statusCode(200)
			.extract()
			.path("find{it.firstName=='"+ firstName+"'}");
		
		System.out.println("The value is: " + studentMapCache);
		
		// assert student with firstName from previous step was returned
		assertThat(studentMapCache, hasValue(firstName));
	}
	
	@Title("Update user info and verify updated info")
	@Test
	public void cUpdateStudent() {
		List<String> courses = new ArrayList<String>();
		courses.add("Java");
		courses.add("C++");
		
		// update firstName
		firstName = firstName + "_updated";
		
		// initialize POJO for update request
		StudentClass student = new StudentClass();
		student.setCourses(courses);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);	
		
		// PUT /{id} update student
		SerenityRest
			.rest()
			.given()
			.log()
			.all()
			.contentType(ContentType.JSON)
			.when()
			.body(student)
			.put("/"+(int)studentMapCache.get("id"))
			.then()
			.log()
			.all()
			.statusCode(200);
		
		// GET /list extract student & cache
		studentMapCache = SerenityRest
				.rest()
				.given()
				.when()
				.get("/list")
				.then()
				.log()
				.all()
				.statusCode(200)
				.extract()
				.path("find{it.firstName=='"+ firstName+"'}");
		
		System.out.println("The value is: " + studentMapCache);
		
		// assert updated name was saved
		assertThat(studentMapCache, hasValue(firstName));
	}
	
	@Title("Delete a student and verify")
	@Test
	public void dDeleteStudent() {
		// DELETE /{id} delete student
		SerenityRest
			.rest()
			.given()
			.when()
			.delete("/" + (int)studentMapCache.get("id"));
		
		// GET /{id} assert 404 not found for deleted student
		SerenityRest
			.rest()
			.given()
			.when()
			.get("/" + (int)studentMapCache.get("id"))
			.then()
			.log()
			.all()
			.statusCode(404);
	}
}
