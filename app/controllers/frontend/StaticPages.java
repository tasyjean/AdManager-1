package controllers.frontend;

import java.util.ArrayList;

import models.data.AdsSize;
import models.data.AdsType;
import models.data.User;
import models.data.UserContact;
import models.data.enumeration.ContactTypeEnum;
import models.data.enumeration.RoleEnum;
import play.*;
import play.mvc.*;

import views.html.*;
import views.html.frontend.*;
import controllers.*;
public class StaticPages extends CompressController {
    
	  
	public static Result home() {
        User user=new User("komputok@gmail.com", "anuanu", "Subyek", "Predikat",  RoleEnum.ADMINISTRATOR);
		user.save();
		User bob = User.find.where().eq("id_user", 21).findUnique();
		
		AdsSize size=new AdsSize("anu", 333, 111, "Untuk ukuran ngasal sumpah");
		
		size.save();
		
		return ok(home.render(""));
    }
	
	public static Result contact(){
		User user=new User("komputok@gmail.com","anuanu","Adnan","Hidayat P",RoleEnum.ADMINISTRATOR);
		ArrayList<UserContact> contacts=new ArrayList<UserContact>();
		UserContact kontak1=new UserContact("08009011", ContactTypeEnum.HOME_PHONE, "Tilpun jika ganteng");
		UserContact kontak2=new UserContact("Jalan Anu Nomor 2", ContactTypeEnum.ADDRESS, "Rumah Ganteng");
		contacts.add(kontak2);
		contacts.add(kontak1);
		user.setUserContact(contacts);
		user.save();

		kontak1.save();
		kontak2.save();
		return ok(contact.render(""));
	}
	
	public static Result registration(){
		
		return ok(registration.render(""));
	}
	
	public static Result help(){
		
		return ok(help.render(""));
	}
	
	public static Result login(){
		
		return ok(login.render(""));
	}
	
	public static Result about(){
		
		return ok(about.render(""));
	}
	
	public static Result logout(){
		
		return null;
	}
}
