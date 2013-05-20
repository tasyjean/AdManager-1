package models.form;

import java.util.*;

import javax.validation.*;

import play.data.validation.Constraints.*;
import javax.persistence.*;

import javax.validation.Constraint;

import play.db.ebean.*;


public class Login {

	@Required
	public String email;
	
	@Required
	public String password;
	
	public String validate(){
		if(email.equals(password)){
			return null;
		}
		return email;
	}
}
