package models.form.frontendForm;

import models.data.User;
import models.service.Authenticator;
import play.data.validation.Constraints.Required;
import play.i18n.Messages;

public class ForgetPassForm {
	
	@Required
	public String email;
	
	public String validate(){
		if(isExist()) return null;
		else return Messages.get("validation.email_not_exist", email);
	} 
	//Tested
	private boolean isExist(){
		User user= User.find.where().eq("email",email).findUnique();
		if(user!=null) return true; else return false;
	}
}
