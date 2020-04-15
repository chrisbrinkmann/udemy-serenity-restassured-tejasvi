package com.udemy.tejasvi.studentapp.cucumber.serenity;

import java.util.HashMap;
import java.util.List;

import com.udemy.tejasvi.studentapp.model.StudentClass;
import com.udemy.tejasvi.studentapp.utils.ReuseableSpecifications;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class StudentSerenitySteps {

	@Step("Creating student with firstName: {0}, lastName:{1}, email:{2}, programme:{3}, courses:{4}")
	public ValidatableResponse createStudent(String firstName, String lastName,
			String email, String programme, List<String> courses) {

		// initialize POJO for create request
		StudentClass student = new StudentClass();
		student.setCourses(courses);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);

		// POST /student create student & check status code
		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericRequestSpec()).when()
				.body(student).post().then(); // then() returns a
												// ValidatableResponse
	}

	@Step("Getting the student object with firstName: {0}")
	public HashMap<String, Object> getStudentByFirstName(String firstName) {
		return SerenityRest.rest().given().when().get("/list").then().log()
				.all().statusCode(200).extract()
				.path("find{it.firstName=='" + firstName + "'}");
	}

	@Step("Updating student with studentID: {0} firstName: {1}, lastName:{2}, email:{3}, programme:{4}, courses:{5}")
	public ValidatableResponse updateStudent(int id, String firstName,
			String lastName, String email, String programme,
			List<String> courses) {

		// initialize POJO for update request
		StudentClass student = new StudentClass();
		student.setCourses(courses);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);

		// PUT /{id} update student
		return SerenityRest.rest().given().log().all()
				.spec(ReuseableSpecifications.getGenericRequestSpec()).when()
				.body(student).put("/" + id).then().log().all().statusCode(200);
	}

	@Step("Delete the student with the studentID:{0}")
	public void deleteStudent(int id) {
		// DELETE /{id} delete student
		SerenityRest.rest().given().when().delete("/" + id);
	}

	@Step("Get student with the ID{0}")
	public ValidatableResponse getStudentById(int id) {
		// GET /{id} assert 404 not found for deleted student
		return SerenityRest.rest().given().when().get("/" + id).then().log()
				.all();
	}
}
