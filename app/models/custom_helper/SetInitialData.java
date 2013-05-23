package models.custom_helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import play.db.DB;

import com.avaje.ebean.Ebean;

import models.data.User;
import models.data.UserContact;
import models.data.UserRole;
import models.data.enumeration.ContactTypeEnum;
import models.data.enumeration.RoleEnum;

/*Set Initial Data, 
 * 
 * Buat ngisi data dalam rangka testing
 */
public class SetInitialData {

	public SetInitialData(){
		//Konstruktor
	}
	
	//Data user + data kontak
	public void setDataUser(){
		
		
		//Clean Data dulu
		deleteUserData();
		try {
			DB.getConnection().createStatement().execute("ALTER SEQUENCE adsmanager.user_seq RESTART WITH 1;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert data");

		//Data 1
		User user1=new User("komputok@gmail.com", "password", "Adnan", "Hidayat", new UserRole(RoleEnum.ADMINISTRATOR));
		user1.setCity("Bandung");
		user1.setCountry("Indonesia");
		user1.setCompany("Teknimo");
		user1.setActive(true);
		
		ArrayList<UserContact> contact1=new ArrayList<UserContact>();
		UserContact kontak_item1=new UserContact("083862263524",ContactTypeEnum.PRIVATE_PHONE,"Hubungi nomor ini jika darurat");
		UserContact kontak_item2=new UserContact("http://www.teknimo.com", ContactTypeEnum.COMPANY_WEBSITE, "Ini websitenya");
		contact1.add(kontak_item1);
		contact1.add(kontak_item2);
		user1.setUserContact(contact1);	
		
		user1.save();
		
		//Data 2
		User user2=new User("gunadarma@gmail.com", "password", "Sayuti", "Hidayat", new UserRole(RoleEnum.ADVERTISER));
		user2.setCity("Jayapandang");
		user2.setCountry("Indonesia");
		user2.setCompany("Sunaya Basah Sejahtera");
		user2.setActive(true);
		
		ArrayList<UserContact> contact2=new ArrayList<UserContact>();
		UserContact kontak2_item1=new UserContact("(021)2132999",ContactTypeEnum.HOME_PHONE,"Hubungi nomor ini jika istri saya tidak dirumah");
		UserContact kontak2_item2=new UserContact("Jalan Sudarmanto nomor 21", ContactTypeEnum.ADDRESS, "Ini websitenya");
		contact2.add(kontak2_item1);
		contact2.add(kontak2_item2);
		user2.setUserContact(contact2);	
		user2.save();
		
		//Data 3
		User user3=new User("sonata@gmail.com", "password", "Hutamala", "Hidayat", new UserRole(RoleEnum.MANAGEMENT));
		user3.setCity("Jakakerto");
		user3.setCountry("Indonesia");
		user3.setCompany("Teknimo");
		user3.setActive(true);
		
		ArrayList<UserContact> contact3=new ArrayList<UserContact>();
		UserContact kontak3_item1=new UserContact("911",ContactTypeEnum.OTHER,"Hanya hubungi ketika darurat");
		UserContact kontak3_item2=new UserContact("http://www.teknimo.com", ContactTypeEnum.COMPANY_WEBSITE, "Ini websitenya");
		contact3.add(kontak3_item1);
		contact3.add(kontak3_item2);
		user3.setUserContact(contact3);	
		user3.save();
	}
	
	public void deleteUserData(){
		
		List<User> data=User.find.all();
		Ebean.delete(data);
		System.out.println("Delete data");
	}

}
