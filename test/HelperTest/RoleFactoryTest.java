package HelperTest;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import models.custom_helper.RoleFactory;
import models.custom_helper.SendMail;
import models.data.UserRole;

import org.junit.Test;

public class RoleFactoryTest {

	@Test
	public void getRole(){
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				RoleFactory factory = new RoleFactory();
				UserRole role1 =factory.getRole("ADMINISTRATOR");
				UserRole role2 =factory.getRole("MANAGER");
				UserRole role3 =factory.getRole("ADVERTISER");
				UserRole role4 =factory.getRole("awawaw");
				System.out.println(role1.getName());
				System.out.println(role2.getName());
				System.out.println(role3.getName());
				System.out.println(role4.getName());
				
			}
		});
	}

}
