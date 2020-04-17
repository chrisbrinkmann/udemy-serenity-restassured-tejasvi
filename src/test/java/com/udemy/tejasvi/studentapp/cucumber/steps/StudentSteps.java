package com.udemy.tejasvi.studentapp.cucumber.steps;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.udemy.tejasvi.studentapp.cucumber.serenity.StudentSerenitySteps;
import com.udemy.tejasvi.studentapp.utils.Utils;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;

public class StudentSteps {

	private ValidatableResponse res;
	private String email;

	@Steps
	StudentSerenitySteps steps;

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost:8080/student";
	}

	@When("^user sends GET request to list endpoint$")
	public void getStudents() {
		res = SerenityRest.rest().when().get("/list").then();
	}

	@Then("^the student app should respond with status code 200$")
	public void verifySuccessStatusCode() {
		res.statusCode(200);
	}

	@When("^user creates a new student with info \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", and \"([^\"]*)\"$")
	public void user_creates_a_new_student_with_info_and(String firstName,
			String lastName, String _email, String programme, String course)
			throws Exception {
		List<String> courses = new ArrayList<String>();
		courses.add(course);

		email = Utils.getRandomIntString() + _email;

		steps.createStudent(firstName, lastName, email, programme, courses)
				.assertThat().statusCode(201);
	}

	@Then("^the new student with \"([^\"]*)\" is verified$")
	public void the_new_student_with_is_verified(String _email) throws Exception {
		HashMap<String, Object> student = steps.getStudentByEmail(email);
		
		assertEquals(email, student.get("email"));
	}
}
