package models.service.user;

import models.data.User;

public class UserDelete {

	public boolean delete(int id_user){
		
		try {
			User user=User.find.byId(id_user);
			user.delete();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
