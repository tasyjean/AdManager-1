package controllers.frontend;

import play.*;
import play.mvc.*;

import views.html.*;
import views.html.frontend.*;
import controllers.*;
public class Frontend extends CompressController {
    
	
	public static Result home() {

		return ok(home.render(""));
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
