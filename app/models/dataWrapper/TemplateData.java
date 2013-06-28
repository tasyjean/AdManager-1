/*
 * 
 * 
 * @Author Adnan HP 
 * Kelas ini untuk data parameter yang umum dilewatkan ke template
 */

package models.dataWrapper;

import java.util.ArrayList;
import java.util.List;

import models.data.User;
import models.service.Authenticator;
import models.service.notification.NotificationCenter;

import play.mvc.Http.Context;


public class TemplateData {

	NotificationCenter notif;
	Context ctx;
	//Data User
	private String frontName="";
	private int id;
	private String lastName="";
	private String role=""; 
	private String email="";
	private String notifCount="";
	private String thumbnailURL="";
	//pilihan tampilan (untuk dashboard)
	private String viewChoice="";

	Authenticator auth = new Authenticator();
	
	public TemplateData(Context ctx, NotificationCenter notif) {
		this.ctx = ctx;
		this.notif=notif;
	}
	public void setUserData()
	{
		User user= auth.getUserLogin(ctx.session());
		if(user!=null){
			this.frontName=user.getFront_name();
			this.lastName=user.getLast_name();
			this.email=user.getEmail();
			this.role=user.getRole().getName();
			this.id=user.getId_user();
			try {
				this.thumbnailURL=user.getProfile_photo().getThumbnailURL();
			} catch (Exception e) {
				this.thumbnailURL="";
			}
			
			//simulasi
			this.notifCount=Integer.toString(notif.countUnread(user.getId_user()));
		}
	}
	public String getFrontName(){
		return frontName;
	}
	public String getLastName(){
		return lastName;
	}
	public String getName(){
		return frontName+" "+lastName;
	}
	public String getRole(){
		return role;
	}
	public String getEmail(){
		return email;
	}
	public String getNotifCount() {
		return notifCount;
	}
	public String getViewChoice() {
		return viewChoice;
	}
	public void setViewChoice(String viewChoice) {
		this.viewChoice = viewChoice;
	}
	public String getThumbnailURL() {
		return thumbnailURL;
	}
	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}
	public int getId(){
		return id;
	}
	
	
}
