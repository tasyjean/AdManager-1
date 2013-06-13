package models.custom_helper;

import java.util.List;

import models.data.UserRole;
import models.data.enumeration.RoleEnum;

public class RoleFactory {

	/*Masukan 
	 * 
	 *  ADMINISTRATOR
	 *  ADVERTISER
	 *  MANAGER
	 */
	//Tested
	public UserRole getRole(String key){
		try {
			RoleEnum enums=RoleEnum.valueOf(key);
			List<UserRole> list=UserRole.find.where().eq("name",enums).findList();
			return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return UserRole.find.byId(3); //default untuk advertiser
		}
	    
	}

}
