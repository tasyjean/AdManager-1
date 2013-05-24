/*
 * 
 * 
 * @Author Adnan HP 
 * Kelas ini untuk data parameter yang umum dilewatkan ke template
 */

package models.custom_helper;

import java.util.ArrayList;
import java.util.List;

import models.data.User;
import models.service.Authentificator;

import play.mvc.Http.Context;


public class TemplateData {

	Context ctx;
	//Data User
	private String frontName="";
	private String lastName="";
	private String role=""; 
	private String email="";
	
	Authentificator auth = new Authentificator();
	
	public TemplateData(Context ctx) {
		this.ctx = ctx;
		
	}
	public void setUserData()
	{
		User user= auth.getUserLogin(ctx.session());
		if(user!=null){
			this.frontName=user.getFront_name();
			this.lastName=user.getLast_name();
			this.email=user.getEmail();
			this.role=user.getRole().getName();
		}
	}
	public String getFrontName(){
		return frontName;
	}
	public String getLastName(){
		return lastName;
	}
	public String getRole(){
		return role;
	}
	public String getEmail(){
		return email;
	}
	
}
