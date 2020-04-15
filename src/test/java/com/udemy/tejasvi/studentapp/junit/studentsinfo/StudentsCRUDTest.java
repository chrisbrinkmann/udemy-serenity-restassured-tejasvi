package com.udemy.tejasvi.studentapp.junit.studentsinfo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.udemy.tejasvi.studentapp.model.StudentClass;
import com.udemy.tejasvi.studentapp.testbase.TestBase;
import com.udemy.tejasvi.studentapp.utils.Utils;

import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;

@RunWith(SerenityRunner.class)
public class StudentsCRUDTest extends TestBase {
	
	static String firstName = "testUser" + Utils.getRandomIntString();
	static String lastName = "testUser" + Utils.getRandomIntString();
	static String programme = "ComputerScience";
	static String email = Utils.getRandomIntString() + "demouser@gmail.com";
	
	@Title("This test will create a new student")
	@Test
	public void createStudent() {
		List<String> courses = new ArrayList<String>();
		courses.add("Java");
		courses.add("C++");
		
		StudentClass student = new StudentClass();
		student.setCourses(courses);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);
		
		
		SerenityRest
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
}
