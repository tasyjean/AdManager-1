package models.form;

import java.util.*;

import javax.validation.*;

import play.data.validation.Constraints.*;
import javax.persistence.*;

import javax.validation.Constraint;

import models.custom_helper.MD5;
import models.data.User;

import play.db.ebean.*;


public class LoginForm {

	public String email;
	
	public String password;
	
	public boolean rememberMe = false;
	
	public String validate(){
		User user=User.find.where()
				.eq("email", email)
				.eq("password", MD5.get().md5(password))
				.findUnique();
		if(user!=null){
			return null;
		}else return "email atau password salah" ;
	} 
}
