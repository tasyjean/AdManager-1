package models.form;

import java.util.*;

import javax.validation.*;

import play.i18n.Messages;
import play.data.validation.Constraints.*;
import javax.persistence.*;

import javax.validation.Constraint;

import java.text.MessageFormat;

import models.custom_helper.MD5;
import models.data.User;
import models.service.Authentificator;

import play.db.ebean.*;


public class LoginForm {
	
	@Required
	public String email;
	@Required
	public String password;
	
	public boolean rememberMe = false;
	
	public String validate(){
		boolean valid=new Authentificator().authenticate(email, password);
		if(valid){
			return null;
		}else{
			 return Messages.get("constraint.login_salah", "apa ini apa itu");
		}
	} 
	
}
