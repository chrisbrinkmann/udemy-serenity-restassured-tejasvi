package com.udemy.tejasvi.studentapp.junit.studentsinfo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.udemy.tejasvi.studentapp.cucumber.serenity.StudentSerenitySteps;
import com.udemy.tejasvi.studentapp.testbase.TestBase;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.junit.annotations.UseTestDataFrom;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("testdata/studentinfo.csv")
public class CreateStudentsDataDrivenTest extends TestBase {
	
	@Steps
	StudentSerenitySteps steps;
	
	private String firstName;
	private String lastName;
	private String email;
	private String programme;
	private String course;
	private List<String> courses;
	
	@Title("This test adds multiple students: DataDriven Test")
	@Test
	public void createMultipleStudents() {
		courses = new ArrayList<String>();
		courses.add(course);
		
		steps.createStudent(firstName, lastName, email, programme, courses)
		.statusCode(201);
			
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProgramme() {
		return programme;
	}

	public void setProgramme(String programme) {
		this.programme = programme;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
}
