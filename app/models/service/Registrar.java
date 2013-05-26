package models.service;

import java.util.Date;

import play.data.Form;
import meesy.Meesy;
import models.custom_helper.SendMail;
import models.data.User;
import models.data.UserRole;
import models.data.enumeration.RoleEnum;
import models.form.frontendForm.RegistrationForm;

/*
 * 
 * Kelas untuk memproses pendaftaran
 */
public class Registrar {
	SendMail mailer=new SendMail();
	String meesyKey="nanana";
	Meesy meesy=new Meesy();
	
	public String register(Form<RegistrationForm> form, String host){
		User user=new User();
		
		user.setEmail(form.get().email);
		user.setFront_name(form.get().front_name);
		user.setLast_name(form.get().last_name);
		user.setActive(false);
		user.setJoin_date(new Date());
		user.setPassword(form.get().password);
		
		UserRole role=new UserRole(RoleEnum.ADVERTISER);
		user.setRole(role);
		
		user.save();
		return sendEmail(user, host);
	}

	//input dalam format UPPERCASE
	public boolean activate(String input, String email){
		//Decript email
		String email_decrypt=meesy.decrypt(email, meesyKey, 0,0);
		try{
			User user=User.find.where().eq("email", email_decrypt).findUnique();
			if(getActivationCode(user).equals(input)){
				user.setActive(true);
				user.update();
				return true;
			}else{
				return false;
			}
		}catch(NullPointerException e){
			return false;
		}
	}
	private String sendEmail(User user,  String host){
		String content="Terima kasih sudah mendaftar di Aplikasi Teknimo Ads Manager<br>Untuk mengaktifkan akun, klik link berikut<br>";
		content=content+generateLink(getActivationCode(user), host, user.getEmail())+
				"<br><br>Salam<br><br>Manajemen Teknimo Ads Manager<br>";
		
		mailer.setRecipient(user.getEmail());
		mailer.setSender("komputok@gmail.com");
		mailer.setContent(content);
		mailer.setSubject("Teknimo Ad Manager : Registrasi Berhasil");
		
		mailer.sendHTML();
		return user.getEmail();
	}	
	private String getActivationCode(User user){
		return user.getValidation_key().toUpperCase();
	}
	private String generateLink(String key,String host, String email){
		String emailCrypt=meesy.encrypt(email, meesyKey, 1, 2, 100);
		
		return "<a href=http://"+host+"/activate/"+emailCrypt+"/"+key+">Link Aktivasi</a>";
	}
}
