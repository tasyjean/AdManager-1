package controllers.frontend;


import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import be.objectify.deadbolt.java.actions.SubjectNotPresent;

import meesy.Meesy;
import models.custom_helper.PasswordGenerator;
import models.custom_helper.RecaptchaRegistration;
import models.custom_helper.RoleFactory;
import models.custom_helper.SendMail;
import models.custom_helper.setting.SettingManager;
import models.dataWrapper.TemplateData;
import models.dataWrapper.setting.SettingData;
import models.form.frontendForm.ForgetPassForm;
import models.form.frontendForm.LoginForm;
import models.form.frontendForm.RegistrationForm;
import models.service.Authenticator;
import models.service.Registrar;
import play.data.Form;
import play.data.validation.ValidationError;
import play.i18n.Messages;
import play.mvc.*;
import views.html.defaultpages.error;
import views.html.frontendView.*;
import controllers.*;
import controllers.action.DataFiller;

public class FrontEndController extends CompressController {
    
	//deklarasi form
    final static Form<LoginForm> loginForm = Form.form(LoginForm.class);
    final static Form<ForgetPassForm> forgetForm = Form.form(ForgetPassForm.class);
    final static Form<RegistrationForm> regisForm = Form.form(RegistrationForm.class);

    static SendMail mail=new SendMail();
    static Meesy meesy=new Meesy();
    static RoleFactory role=new RoleFactory();
    static PasswordGenerator pass_gen=new PasswordGenerator();
    static Registrar registrar = new Registrar(mail,meesy,role, pass_gen);
    static Authenticator auth=new Authenticator();
    
    static SettingManager manager=new SettingManager();
    
	@With(DataFiller.class)
	public static Result home() {
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		SettingData datas=new SettingData(3,manager);
		String content1=datas.aboutValue;
		String content2=datas.helpValue;
		
		return ok(home.render(data, content1,content2));
    }

	@With(DataFiller.class)
	public static Result contact(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");
		SettingData datas=new SettingData(3,manager);
		String content=datas.contactValue;
		return ok(contact.render(data, content));
	}
	@SubjectNotPresent
	@With(DataFiller.class)
	public static Result registration(){
		
		return ok(registration.render(regisForm));
	}
	public static Result registrar(){
		Form<RegistrationForm> form = Form.form(RegistrationForm.class).bindFromRequest();
		if(form.hasErrors()){
			return badRequest(registration.render(form));
		}else{
			//verifikasi recapcha
			if(!RecaptchaRegistration.get().validate(form, request().remoteAddress())){
				List<ValidationError> list=new ArrayList<ValidationError>();
				list.add(new ValidationError("captcha", Messages.get("validation.captcha")));
				form.errors().put("captcha", list);
				return badRequest(registration.render(form));
			}
			String key=registrar.register(form, request().getHeader("HOST")); 
			return ok(registration_success.render(key, form.get().front_name));
		}
	}
	public static Result activate(String email, String key){
		boolean sukses=registrar.activate(key, email);
		if(sukses){
			return ok(activation.render("success"));
		}else return ok(activation.render("failed"));
	}
	@With(DataFiller.class)
	public static Result help(){
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		SettingData datas=new SettingData(3,manager);
		String content2=datas.helpValue;
		return ok(help.render(data, content2));
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
		SettingData datas=new SettingData(3,manager);
		String content1=datas.aboutValue;

		return ok(about.render(data, content1));
	}
	
	@With(DataFiller.class)
	public static Result logout(){
		auth.logout(session());
		TemplateData data = (TemplateData) 
				Http.Context.current().args.get("templateData");	
		SettingData datas=new SettingData(3,manager);
		String content1=datas.aboutValue;
		String content2=datas.helpValue;
		return ok(home.render(data, content1, content2));
	}
	public static Result forgetPassword(){
		return ok(forget_password.render(forgetForm));
	}
	public static Result forgetProcess(){
		Form<ForgetPassForm> forgetForm = Form.form(ForgetPassForm.class).bindFromRequest();
		flash().put("status", "success");
		if(forgetForm.hasErrors()){
			flash("error","Email tidak terdaftar ");
			return ok(forget_password.render(forgetForm));
		}else{
			boolean sukses=registrar.forgetPassword(forgetForm.get().email);
			if(sukses){
				flash("success","Reset password berhasil, password baru telah dikirim ke "+forgetForm.get().email);
				return ok(forget_password.render(forgetForm));
			}else{
				flash("error","Reset password gagal, silahkan hubungi administrator " +
						"("+Messages.get("email.sender")+") untuk info lebih lanjut");
				return ok(forget_password.render(forgetForm));				
			}
		}
	}
	
	
}
