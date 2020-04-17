@feature1
Feature: Student app REST api

@smoke @get-students
Scenario: User gets a list of students
	When user sends GET request to list endpoint
	Then the student app should respond with status code 200

@create-students
Scenario Outline: User creates a new student
	When user creates a new student with info "<firstName>", "<lastName>", "<email>", "<programme>", and "<courses>"
	Then the new student with "<email>" is verified
	
	Examples:
		| firstName | lastName | email      | programme | courses |
		| Joe       | Guage    | jg@123.com | CS        | Java    |
		| Mary      | Sue      | ms@123.com | IS        | Java2   |