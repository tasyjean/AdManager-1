package models.service.user;

import play.Play;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Http.MultipartFormData.FilePart;
import models.custom_helper.DomainURL;
import models.custom_helper.EmailSenderThread;
import models.custom_helper.RoleFactory;
import models.custom_helper.SendMail;
import models.custom_helper.file_manager.FileManager;
import models.custom_helper.file_manager.SaveToEnum;
import models.data.FileUpload;
import models.data.User;
import models.data.UserContact;
import models.data.UserRole;
import models.data.enumeration.ContactTypeEnum;
import models.data.enumeration.RoleEnum;
import models.form.backendForm.userForm.ContactForm;
import models.form.backendForm.userForm.EditUserForm;
import models.form.backendForm.userForm.UserForm;

public class UserCreator {

	FileManager manager;
	SendMail mailer;
	RoleFactory factory;
	public UserCreator(FileManager manager, SendMail mailer, RoleFactory factory){
		this.manager=manager;
		this.mailer=mailer;
		this.factory=factory;
	}
	
	public User saveUser(Form<UserForm> form){
		User user;
		try {
			user = new User();
			UserRole role=factory.getRole(form.get().role);
			user.setActive(true);
			user.setFront_name(form.get().front_name);
			user.setLast_name(form.get().last_name);
			user.setEmail(form.get().email);
			user.setCompany(form.get().company);
			user.setCity(form.get().city);
			user.setCountry(form.get().country);
			user.setRole(role);
			user.setPassword(form.get().password);
			user.save();
			sendEmail(user,form.get().password);
			return user;
			
		} catch (Exception e) {
			return null;
		}
	}
	public User updateUser(Form<EditUserForm> form, int user_id){
		User user;
		try {
			user =User.find.byId(user_id);
			UserRole role=factory.getRole(form.get().role);
			System.out.println(RoleEnum.valueOf(form.get().role));
			user.setFront_name(form.get().front_name);
			user.setLast_name(form.get().last_name);
			user.setCompany(form.get().company);
			user.setCity(form.get().city);
			user.setCountry(form.get().country);
			user.setRole(role);
			
			user.update();
			
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public boolean saveContact(Form<ContactForm> contact, int id_user){
		try {
			UserContact new_contact=new UserContact();
			new_contact.setContact_value(contact.get().value);
			new_contact.setContact_type(ContactTypeEnum.valueOf(contact.get().contact_type));
			new_contact.setContact_description(contact.get().description);
			new_contact.setId_user(User.find.byId(id_user));

			new_contact.save();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	public UserContact editContact(Form<ContactForm> contact, int id_contact){
		try {
			UserContact edit_contact=UserContact.find.byId(id_contact);
			edit_contact.setContact_value(contact.get().value);
			edit_contact.setContact_type(ContactTypeEnum.valueOf(contact.get().contact_type));
			edit_contact.setContact_description(contact.get().description);
			
			edit_contact.update();
			return edit_contact;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//Kode Error : 
	//0 : sukses;
	//1 : exception;
	//2 : format nggak sesuai
	public int saveProfilePicture(FilePart part, int id_user){
		try {
			//validasi type
			String filetype=part.getContentType();
			if(filetype.equals("image/png") || 
			   filetype.equals("image/jpeg") || 
			   filetype.equals("image/gif"))  {
			
				User user=User.find.byId(id_user);
				FileUpload upload=manager.saveNew(part, SaveToEnum.PROFILE_PICTURE);
				if(user.getProfile_photo()!=null){
					FileUpload existing=user.getProfile_photo();
					user.setProfile_photo(upload);
					user.update();
					existing.delete();
				}else{
					user.setProfile_photo(upload);		
					user.update();
				}
				manager.saveThumbnail(upload.getId());
				
				return 0;	//sukses			
			} else return 2; //jika ngga sesuai format

		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	
	private boolean sendEmail(User user, String password){
		try {
			String content=Messages.get("email.registered.content", 
										 DomainURL.get()+"/login",
										 password, 
										 user.getRole().getName());
			
			mailer.setRecipient(user.getEmail());
			mailer.setSender(Messages.get("email.sender"));
			mailer.setContent(content);
			mailer.setSubject(Messages.get("email.registered.subject"));
			mailer.setCc(Messages.get("email.sender"));
			
			EmailSenderThread sender = new EmailSenderThread(mailer);
			new Thread(sender).start();
			
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
		
		return true;
	}	

}
