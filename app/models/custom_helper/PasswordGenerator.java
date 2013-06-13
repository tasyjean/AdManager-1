package models.custom_helper;

import java.util.Random;

public class PasswordGenerator {

	public String getRandom()
	{
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);		    
		}
		String output = sb.toString();
		return output;	
	}

}
