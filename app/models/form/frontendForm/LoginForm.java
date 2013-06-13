package models.form.frontendForm;

import java.util.*;

import javax.validation.*;

import play.Logger;
import play.i18n.Messages;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;

import javax.persistence.*;

import javax.validation.Constraint;

import java.text.MessageFormat;

import models.custom_helper.MD5;
import models.data.User;
import models.service.Authenticator;

import play.db.ebean.*;


public class LoginForm {
	
	public String email;
	public String password;
	public boolean rememberMe = false;
	
	public String validate(){
		boolean valid=new Authenticator().authenticate(email, password);
		if(isFilled()){
			if(valid){
				return null;
			}else{
				 return Messages.get("constraint.login_salah", "apa ini apa itu");
			}
		}else{
			return Messages.get("constraint.required", "");
		}
	} 
   private boolean isFilled(){
	   Logger.info("hasilnya " + email +" d "+password);
	   
	   if(email.equals("")) return false;
	   
	   if(!(email!=null)) return false;
	   
	   if(password.equals("")) return false;
	   
	   if(!(password!=null)) return false;
	   
	   return true;
   }
	
}
