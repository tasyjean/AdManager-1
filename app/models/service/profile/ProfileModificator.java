package models.service.profile;

import models.custom_helper.MD5;
import models.custom_helper.RoleFactory;
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
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Http.MultipartFormData.FilePart;

public class ProfileModificator {

	RoleFactory factory;
	FileManager manager;
	
	public ProfileModificator(RoleFactory factory, FileManager  manager) {
		this.factory=factory;
		this.manager=manager;
	}
	public User updateProfile(Form<EditUserForm> form, User user){
		try {
			
			UserRole role=factory.getRole(form.get().role);
			user.setFront_name(form.get().front_name);
			user.setLast_name(form.get().last_name);
			user.setCompany(form.get().company);
			user.setCity(form.get().city);
			user.setCountry(form.get().country);
			user.update();
			
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public boolean saveContact(Form<ContactForm> contact, User user){
		try {
			UserContact new_contact=new UserContact();
			new_contact.setContact_value(contact.get().value);
			new_contact.setContact_type(ContactTypeEnum.valueOf(contact.get().contact_type));
			new_contact.setContact_description(contact.get().description);
			new_contact.setId_user(user);

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
	public int saveProfilePicture(FilePart part, User user){
		try {
			//validasi type
			String filetype=part.getContentType();
			if(filetype.equals("image/png") || 
			   filetype.equals("image/jpeg") || 
			   filetype.equals("image/gif"))  {
			
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
	
	//kembalikan id user
	public int deleteContact(int id_contact) throws Exception{
		UserContact contact=UserContact.find.byId(id_contact);
		int id_user=contact.getId_user().getId_user();
		contact.delete();
		return id_user;
	}
	
	/*
	 * 0 jika sukses
	 * 1 jika password salah
	 * 2 jika error password tidak sama
	 * 3 error lain
	 */
	public int changePassword(String old_password, 
							  String new_password, 
							  String new_password_repeat,
							  User user){
		try {
			boolean isValid=user.getPassword().
								equals(MD5.get().md5(old_password));
			boolean isValid2=new_password.equals(new_password_repeat);
			if(isValid){
				if(isValid2){
					user.setPassword(new_password);
					user.update();
					return 0;					
				}else{
					return 2;
				}
			}else{
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}		
	}
	

}
