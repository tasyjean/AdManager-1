package controllers.frontend;

import java.util.ArrayList;

import models.data.AdsSize;
import models.data.AdsType;
import models.data.User;
import models.data.UserContact;
import models.data.enumeration.ContactTypeEnum;
import models.data.enumeration.RoleEnum;
import models.form.LoginForm;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import views.html.helper.form;

import views.html.frontendView.*;
import controllers.*;

public class FrontEndController extends CompressController {
    
    final static Form<LoginForm> loginForm = Form.form(LoginForm.class);
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
	public static Result authenticate(){
		Form<LoginForm>  loginForm = Form.form(LoginForm.class).bindFromRequest();
		if(loginForm.hasErrors()) {
			loginForm.error("errrooor");
            return ok(home.render(loginForm.globalError().message()));
        } else {
        	String email=loginForm.get().email;
    		String password=loginForm.get().password;
    		boolean status = loginForm.get().rememberMe;
    		if(status){
    			
    		}
    		return ok(home.render(email+" "+password+" "+status));

        }
	}
	
	public static Result login(){
		return ok(login.render(loginForm));
	}
	
	public static Result about(){
		
		return ok(about.render(""));
	}
	
	public static Result logout(){
		
		return null;
	}
}
