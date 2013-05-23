package controllers.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.objectify.deadbolt.java.actions.SubjectNotPresent;

import models.data.AdsSize;
import models.data.AdsType;
import models.data.User;
import models.data.UserContact;
import models.data.enumeration.ContactTypeEnum;
import models.data.enumeration.RoleEnum;
import models.form.LoginForm;
import models.service.Authentificator;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import views.html.helper.form;

import views.html.backendView.dashboard_index;
import views.html.frontendView.*;
import controllers.*;

public class FrontEndController extends CompressController {
    
    final static Form<LoginForm> loginForm = Form.form(LoginForm.class);
    
    static Authentificator auth=new Authentificator();
    
	public static Result home() {
		User user= auth.getUserLogin(session());
		Map<String, String> map = new HashMap<String, String>();
		map.put("frontname", user.getFront_name()+" "+user.getLast_name());
		
		return ok(home.render(map));
    }

	public static Result contact(){
	
		return ok(contact.render(""));
	}
	@SubjectNotPresent
	public static Result registration(){
		
		return ok(registration.render(""));
	}
	
	public static Result help(){
		return ok(help.render(""));
	}
	public static Result authenticate(){
		Form<LoginForm>  loginForm = Form.form(LoginForm.class).bindFromRequest();
		if(loginForm.hasErrors()) {
//            return ok(home.render(loginForm.globalErrors().get(0).message()));
			  return ok(home.render(new HashMap<String, String>()));
				
        } else {
        	auth.login(loginForm.get().email, session());
    		return redirect(controllers.backend.routes.Backend.index());
        }
	}
	@SubjectNotPresent
	public static Result login(){
		return ok(login.render(loginForm));
	}
	
	public static Result about(){
		
		return ok(about.render(""));
	}
	
	public static Result logout(){
		auth.logout(session());
//		return ok(home.render(""));
		return ok(contact.render(""));

	}
	
}
