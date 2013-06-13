package models.service.user;

import play.i18n.Messages;
import models.custom_helper.DomainURL;
import models.custom_helper.PasswordGenerator;
import models.custom_helper.SendMail;
import models.data.User;
import models.data.UserContact;

public class UserOperation {
	
	PasswordGenerator pass_gen;
	SendMail mailer;

	
	public UserOperation(PasswordGenerator pass_gen, SendMail mailer) {
		this.pass_gen = pass_gen;
		this.mailer = mailer;
	}
	public boolean deleteUser(int id_user){
		
		try {
			User user=User.find.byId(id_user);
			user.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	//kembalikan id user
	public int deleteContact(int id_contact) throws Exception{
		UserContact contact=UserContact.find.byId(id_contact);
		int id_user=contact.getId_user().getId_user();
		contact.delete();
		return id_user;
	}
	public User activate(int id_user){
		try {
			User user=User.find.byId(id_user);
			if(user.isActive()){
				user.setActive(false);
			}else{
				user.setActive(true);
			}
			user.update();
			return user;
		} catch (Exception e) {
			return null;
		}
	}
	//kembalikan password
	public String resetPassword(int id_user){
		try {
			User user=User.find.byId(id_user);
			String password=pass_gen.getRandom();
			//pastikan email sudah dikirim sebelum password diganti
			if(sendEmailPassword(user, password)){
				user.setPassword(password);	
				user.update();
			}else{
				return null;
			}
			return password;
		} catch (Exception e) {
			return null;
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
			
			mailer.sendHTML();
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
		
	}
	

}
