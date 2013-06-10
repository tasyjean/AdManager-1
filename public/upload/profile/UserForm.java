package models.form.backendForm.user;

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

public class UserForm {
	
	//identifikasi field
	@Email(message="Format email salah")
	public String email;
	public String password;
	public String password_repeat;
	public String front_name;
	public String last_name;
	public String company;
	public int role;
	public int current_balance;
	public boolean isActive=true;
	public int profile_photo;
	public String city;
	public String country;
	public List<Integer> userContact; 

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
		if(password.equals("")){
			isError = true;
			error.add(new ValidationError("password", Messages.get("validation.required")));			
		}else
		if(!password.equals(password_repeat)){
			isError = true;
			error.add(new ValidationError("password_sama", Messages.get("validation.password")));
		}
		if(isError) return error; else return null;
	}
	//BEHAVIOUR: Jika email sudah ada, tapi belum aktif, maka data sebelumnya dihapus
	private boolean isDuplicate(){
		User user= User.find.where().eq("email",email).findUnique();
		
		try{
			if(user.isActive()){
				return true;
			}else{
				user.delete();
				return false;
			}			
		}catch(NullPointerException e){
			return false;
		}
	}
	
}
