package models.service;

import models.custom_helper.MD5;
import models.data.User;
import models.form.frontendForm.LoginForm;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Http.Context;
import play.mvc.Http.Session;

//Menangani log in dan logout, dan authentifikasi
public class Authentificator {

	private final String email_id="email";
	
	public void login(String email, Session session){
		session.put(email_id, email);
	}
	public void logout(Session session){
		session.remove(email_id);
	}
	//mendapatkan object user yang lagi login
	public User getUserLogin(Session session){
		String email=session.get(email_id);
		return User.find.where().eq("email", email).findUnique();
	}
	//Lagi login nggak
	public boolean isLogin(Session session){
		return session.containsKey(email_id);
	}
	//authentifikasi user
	public boolean authenticate(String email, String password){
		User user=User.find.where()
				.eq("email", email)
				.eq("password", MD5.get().md5(password))
				.findUnique();
		if(user!=null){
			return (user.isActive()) ? true: false; //harus aktif dulu
		}else{
			return false;
		}
	}
}
