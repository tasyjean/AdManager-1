package models.form.frontendForm;

import models.service.Authentificator;
import play.data.validation.Constraints.Required;
import play.i18n.Messages;

public class ForgetPassForm {
	
	@Required
	public String email;
	
	public String validate(){
		return null;
	} 
}
