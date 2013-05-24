package controllers.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.Current;

import be.objectify.deadbolt.java.actions.SubjectNotPresent;

import models.custom_helper.TemplateData;
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
		TemplateData data=new TemplateData(Http.Context.current());
		data.setUserData();
		return ok(home.render(data));
    }

	public static Result contact(){
		TemplateData data=new TemplateData(Http.Context.current());
		data.setUserData();	
		return ok(contact.render(data));
	}
	@SubjectNotPresent
	public static Result registration(){
		TemplateData data=new TemplateData(Http.Context.current());
		data.setUserData();		
		return ok(registration.render(data));
	}
	
	public static Result help(){
		TemplateData data=new TemplateData(Http.Context.current());
		data.setUserData();		
		return ok(help.render(data));
	}
	public static Result authenticate(){
		Form<LoginForm>  loginForm = Form.form(LoginForm.class).bindFromRequest();
		if(loginForm.hasErrors()) {
			TemplateData data=new TemplateData(Http.Context.current());
			data.setUserData();
			return ok(login.render(loginForm,data));
        } else {
        	auth.login(loginForm.get().email, session());
    		return redirect(controllers.backend.routes.DashboardController.index());
        }
	}
	@SubjectNotPresent
	public static Result login(){
		TemplateData data=new TemplateData(Http.Context.current());
		data.setUserData();
		return ok(login.render(loginForm,data));
	}
	
	public static Result about(){
		TemplateData data=new TemplateData(Http.Context.current());
		data.setUserData();
		return ok(about.render(data));
	}
	
	public static Result logout(){
		auth.logout(session());
		TemplateData data=new TemplateData(Http.Context.current());
		data.setUserData();
		return ok(contact.render(data));

	}
	
}
