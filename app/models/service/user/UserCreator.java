package models.service.user;

import play.data.Form;
import play.mvc.Http.MultipartFormData.FilePart;
import models.custom_helper.file_manager.FileManager;
import models.data.User;
import models.data.UserContact;

public class UserCreator {

	FileManager manager;
	public UserCreator(FileManager manager){
		this.manager=manager;
	}
	
	public User saveUser(Form<User> form){
		User user=new User();
		return null;
	}
	
	public User updateUser(Form<User> form){
		return null;
	}
	
	public boolean saveContact(Form<UserContact>[] user){
		
		return false;
		
	}
	public int saveProfilePicture(FilePart part){
		return 0;
	}
	
	public int updateProfilePicture(FilePart part, int id_user){
		return 0;
	}

}
