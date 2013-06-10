package model;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.ArrayList;
import java.util.List;

import models.data.BannerSize;
import models.data.Campaign;
import models.data.User;
import models.data.UserContact;
import models.data.enumeration.ContactTypeEnum;
import models.data.enumeration.RoleEnum;

import org.junit.Test;

import com.avaje.ebean.common.BeanList;

public class UserTest {
	@Test
	public void insertData(){
		
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				
				
				
//				kontak1.save();
//				kontak2.save();							
			}
        });
	}
	@Test
	public void insertContact(){
		running(fakeApplication(), new Runnable() {
			
			public void run() {
			UserContact kontak1=new UserContact("anuanua", ContactTypeEnum.HOME_PHONE, "Tilpsadasdusdsf jisdfdfhdfhfdhdsfka ganteng");
				kontak1.setId_user(User.find.byId(21));
				kontak1.save();
			};
			
		});
	}
	public void testRead(){
		running(fakeApplication(), new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				User user= User.find.byId(81);
				BeanList<UserContact> x= (BeanList<UserContact>)user.getUserContact();
				System.out.println("Anu : ");
				
				for(UserContact anu:x){
					System.out.println("Anu : "+anu.getContact_value());
				}
				assertThat(user).isNotNull();
			}
		});

	}
	
	public void testDelete(){
		running(fakeApplication(), new Runnable() {
			
			@Override
			public void run() {
				User user=User.find.byId(141);
				user.delete();
				assertThat(User.find.byId(141)).isNull();
			}
		});
	}

}
