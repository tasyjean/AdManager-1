package models.form.backendForm.userForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.data.FileUpload;
import models.data.User;
import models.data.UserContact;
import models.data.UserPermission;
import models.data.UserRole;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Email;
import play.data.validation.ValidationError;
import play.i18n.Messages;

public class EditUserForm {
	
	//identifikasi field
	public String id;
	public String front_name;
	public String last_name;
	public String company;
	public String role;
	public String city;
	public String country;
	public List<ValidationError> validate(){
		//Validasi front name last name email
		boolean isError=false;
		List<ValidationError> error= new ArrayList<ValidationError>();
		if(front_name.equals("")){
			isError=true;
			error.add(new ValidationError("front_name", Messages.get("validation.required")));
		}
		if(isError) return error; else return null;
	}
}
