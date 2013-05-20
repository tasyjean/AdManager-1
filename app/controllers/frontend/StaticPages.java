package controllers.frontend;

import models.data.User;
import play.*;
import play.mvc.*;

import views.html.*;
import views.html.frontend.*;
import controllers.*;
public class StaticPages extends CompressController {
    
	  
	public static Result home() {
		User user=new User();
		user.city="uninisaidjlasjfk";
		user.email="asfafasf";
		user.password="asajdkalsdk";
		
		user.save();
		User anu= User.find.byId((long) 21);
		String anu2=anu.city;
		
		return ok(home.render(anu2));
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
