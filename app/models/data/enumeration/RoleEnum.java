package models.data.enumeration;

import com.avaje.ebean.annotation.EnumValue;

public enum RoleEnum {

	@EnumValue("administrator")
	ADMINISTRATOR,
	
	@EnumValue("manager")
	MANAGER, 
	 
	@EnumValue("advertiser")
	ADVERTISER
	
}
