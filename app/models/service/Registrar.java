package models.service;

import java.util.Date;

import play.Logger;
import play.data.Form;
import play.i18n.Messages;
import meesy.Meesy;
import models.custom_helper.DomainURL;
import models.custom_helper.EmailSenderThread;
import models.custom_helper.PasswordGenerator;
import models.custom_helper.RoleFactory;
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
	SendMail mailer;
	final String MEESY_KEY="nanana";
	Meesy meesy;
	RoleFactory factory;
	PasswordGenerator pass_gen;
	
	public Registrar(SendMail mailer, Meesy meesy, RoleFactory factory, PasswordGenerator pass_gen) {
		super();
		this.pass_gen=pass_gen;
		this.mailer = mailer;
		this.meesy = meesy;
		this.factory = factory;
	}

	public String register(Form<RegistrationForm> form, String host){
		User user=new User();
		
		user.setEmail(form.get().email);
		user.setFront_name(form.get().front_name);
		user.setLast_name(form.get().last_name);
		user.setActive(false);
		user.setJoin_date(new Date());
		user.setPassword(form.get().password);
		
		UserRole role=UserRole.find.where().eq("name",RoleEnum.ADVERTISER.name().toLowerCase()).findUnique();
		user.setRole(role);
		
		user.save();
		return sendEmail(user, host);
	}

	//input dalam format UPPERCASE
	public boolean activate(String input, String email){
		//Decript email
		String email_decrypt=meesy.decrypt(email, MEESY_KEY, 0,0);
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
		
		EmailSenderThread sender = new EmailSenderThread(mailer);
		new Thread(sender).start();
		
		return user.getEmail();
	}	
	private String getActivationCode(User user){
		return user.getValidation_key().toUpperCase();
	}
	private String generateLink(String key,String host, String email){
		String emailCrypt=meesy.encrypt(email, MEESY_KEY, 1, 2, 100);
		
		return "<a href=http://"+host+"/activate/"+emailCrypt+"/"+key+">Link Aktivasi</a>";
	}
	public Boolean forgetPassword(String email){
		try {
			Logger.error(email);
			User user= User.find.where().eq("email",email).findUnique();			
			String password=pass_gen.getRandom();
			//pastikan email sudah dikirim sebelum password diganti
			if(sendEmailPassword(user, password)){
				user.setPassword(password);	
				user.update();
			}else{
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	private boolean sendEmailPassword(User user, String password){
		try {
			String content=Messages.get("email.reset.content", 
										 DomainURL.get()+"/login",
										 password);
			
			mailer.setRecipient(user.getEmail());
			mailer.setSender(Messages.get("email.sender"));
			mailer.setContent(content);
			mailer.setSubject(Messages.get("email.reset.subject"));
			mailer.setCc(Messages.get("email.sender"));
			
			EmailSenderThread sender = new EmailSenderThread(mailer);
			new Thread(sender).start();
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
		
	}
}
