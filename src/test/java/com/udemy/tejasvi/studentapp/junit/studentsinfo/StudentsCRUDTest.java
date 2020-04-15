package com.udemy.tejasvi.studentapp.junit.studentsinfo;

import static org.hamcrest.Matchers.hasValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.udemy.tejasvi.studentapp.cucumber.serenity.StudentSerenitySteps;
import com.udemy.tejasvi.studentapp.testbase.TestBase;
import com.udemy.tejasvi.studentapp.utils.ReuseableSpecifications;
import com.udemy.tejasvi.studentapp.utils.Utils;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentsCRUDTest extends TestBase {

	@Steps
	StudentSerenitySteps steps;

	static String firstName = "testUser" + Utils.getRandomIntString();
	static String lastName = "testUser" + Utils.getRandomIntString();
	static String programme = "ComputerScience";
	static String email = Utils.getRandomIntString() + "demouser@gmail.com";
	static List<String> courses = new ArrayList<String>();
	static HashMap<String, Object> studentMapCache;

	@Title("This test will create a new student")
	@Test
	public void aCreateStudent() {
		courses.add("Java");
		courses.add("C++");

		steps.createStudent(firstName, lastName, email, programme, courses)
				.statusCode(201)
				.spec(ReuseableSpecifications.getGenericResponseSpec());
	}

	@Title("This test will verify a the student in the previous test was added")
	@Test
	public void bGetStudent() {
		// GET /list extract student & cache
		studentMapCache = steps.getStudentByFirstName(firstName);

		System.out.println("The value is: " + studentMapCache);

		// assert student with firstName from previous step was returned
		assertThat(studentMapCache, hasValue(firstName));
	}

	@Title("Update user info and verify updated info")
	@Test
	public void cUpdateStudent() {
		// update firstName cache
		firstName = firstName + "_updated";

		// PUT /{id} update student info by id
		steps.updateStudent((int)studentMapCache.get("id"), firstName, lastName, email, programme, courses);

		// GET /list extract student & cache
		studentMapCache = steps.getStudentByFirstName(firstName);

		System.out.println("The value is: " + studentMapCache);

		// assert updated name was saved
		assertThat(studentMapCache, hasValue(firstName));
	}

	@Title("Delete a student and verify")
	@Test
	public void dDeleteStudent() {
		// DELETE /{id} delete student
		steps.deleteStudent((int)studentMapCache.get("id"));

		// GET /{id} assert 404 not found for deleted student
		steps.getStudentById((int)studentMapCache.get("id"))
			.statusCode(404)
			.spec(ReuseableSpecifications.getGenericResponseSpec());
	}
}
