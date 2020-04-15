package com.udemy.tejasvi.studentapp.utils;

import java.util.Random;

public class Utils {
	
	public static String getRandomIntString() {
		Random random = new Random();
		int randomInt = random.nextInt(90000);
		
		return Integer.toString(randomInt);
	}
}
