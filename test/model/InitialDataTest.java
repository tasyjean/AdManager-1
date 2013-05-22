package model;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import models.custom_helper.SetInitialData;

import org.junit.Test;

public class InitialDataTest {
	
	@Test
	public void testUser(){
		running(fakeApplication(), new Runnable() {
			
		@Override
		public void run() {
			new SetInitialData().setDataUser();
		}

	});
}
	public void testDeleteUser(){
		running(fakeApplication(), new Runnable() {
				
			@Override
			public void run() {
				new SetInitialData().deleteUserData();
			}

		});	
	}}
