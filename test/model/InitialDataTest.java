package model;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import models.custom_helper.SetInitialData;
import models.data.User;

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
				
				User user1=User.find.byId(1);
				User user2=User.find.byId(2);
				boolean x;
				if(user1.getJoin_date().after(user2.getJoin_date())){
					System.out.println(user1.getJoin_date().toString() + user1.getJoin_date().getTime());
				}
				System.out.println(user1.getJoin_date().toString() + user1.getJoin_date().getTime());
				
				
			}
		});	
	}}
