package models.form.frontendForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.data.User;

import play.data.validation.Constraints.Email;
import play.data.validation.ValidationError;
import play.i18n.Messages;

public class RegistrationForm {

	public String front_name;
	public String last_name;
	@Email
	public String email;
	public String password;
	public String password_repeat;
	public String capctha;

	public List<ValidationError> validate(){
		//Validasi front name last name email
		boolean isError=false;
		List<ValidationError> error= new ArrayList<ValidationError>();
		if(front_name.equals("")){
			isError=true;
			error.add(new ValidationError("frontName", Messages.get("validation.required")));
		}
		//validasi email
		if(email.equals("")){
			isError=true;
			error.add(new ValidationError("email", Messages.get("validation.required")));
		}else if(isDuplicate()){
			isError=true;
			error.add(new ValidationError("email", Messages.get("validation.email_exist")));
		}
		//validasi password
		
		return null;
		
	}
	//Tested
	private boolean isDuplicate(){
		User user= User.find.where().eq("email",email).findUnique();
		if(user!=null) {
			return true;
		} else {
			return false;
		}
	}
}
