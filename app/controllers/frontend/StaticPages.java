package controllers.frontend;

import models.data.AdsSize;
import models.data.AdsType;
import models.data.User;
import models.data.enumeration.RoleEnum;
import play.*;
import play.mvc.*;

import views.html.*;
import views.html.frontend.*;
import controllers.*;
public class StaticPages extends CompressController {
    
	  
	public static Result home() {
        User user=new User("komputok@gmail.com", "anuanu", "Subyek", "Predikat", "PT ANU", RoleEnum.ADMINISTRATOR);
		user.save();
		User bob = User.find.where().eq("id_user", 21).findUnique();
		
		AdsSize size=new AdsSize("anu", 333, 111, "Untuk ukuran ngasal sumpah");
		
		size.save();
		
		return ok(home.render(bob.email));
    }
	
	public static Result contact(){
		
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
