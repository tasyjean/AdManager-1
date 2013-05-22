package HelperTest;

import java.util.Random;

import models.custom_helper.MD5;

import org.junit.Test;

public class MD5Test {


	@Test
	public void testMD5(){
		MD5 x=MD5.get();
		System.out.println(x.md5("anu"));
		
		System.out.println(new Random().nextInt());
		Random xx=new Random();
		int y=xx.nextInt();
		System.out.println(MD5.get().md5(String.valueOf(y)));

	}
}
