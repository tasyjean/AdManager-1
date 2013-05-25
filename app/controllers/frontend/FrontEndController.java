package controllers.frontend;


import com.google.inject.Inject;

import be.objectify.deadbolt.java.actions.SubjectNotPresent;

import models.custom_helper.SendMail;
import models.dataWrapper.TemplateData;
import models.form.frontendForm.ForgetPassForm;
import models.form.frontendForm.LoginForm;
import models.form.frontendForm.RegistrationForm;
import models.service.Authentificator;
import play.data.Form;
import play.mvc.*;
import views.html.frontendView.*;
import controllers.*;
import controllers.action.DataFiller;

public class FrontEndController extends CompressController {
    
	//deklarasi form
    final static Form<LoginForm> loginForm = Form.form(LoginForm.class);
    final static Form<ForgetPassForm> forgetForm = Form.form(ForgetPassForm.class);
    final static Form<RegistrationForm> regisForm = Form.form(RegistrationForm.class);
    
    static Authentificator auth=new Authentificator();
    
    
	@With(DataFiller.class)
	public static Result home() {
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		return ok(home.render(data));
    }

	@With(DataFiller.class)
	public static Result contact(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		
		return ok(contact.render(data));
	}
	@SubjectNotPresent
	@With(DataFiller.class)
	public static Result registration(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		
		return ok(registration.render(data));
	}
	public static Result registrar(){
		
		return ok();
	}
	@With(DataFiller.class)
	public static Result help(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		return ok(help.render(data));
	}
	@With(DataFiller.class)
	public static Result authenticate(){
		Form<LoginForm>  loginForm = Form.form(LoginForm.class).bindFromRequest();
		if(loginForm.hasErrors()) {
			return ok(login.render(loginForm));
        } else {
        	auth.login(loginForm.get().email, session());
    		return redirect(controllers.backend.routes.DashboardController.index());
        }
	}
	@SubjectNotPresent
	@With(DataFiller.class)
	public static Result login(){
		return ok(login.render(loginForm));
	}
	@With(DataFiller.class)	
	public static Result about(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		return ok(about.render(data));
	}
	
	@With(DataFiller.class)
	public static Result logout(){
		auth.logout(session());
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		return ok(home.render(data));

	}
	public static Result forgetPassword(){
		return ok(forget_password.render(forgetForm));
	}
	public static Result forgetProcess(){
		Form<ForgetPassForm> forgetForm = Form.form(ForgetPassForm.class).bindFromRequest();
		flash().put("status", "success");
		// TODO prosesing proses forget password
		return ok(forget_password.render(forgetForm));
	}
	
	
}
