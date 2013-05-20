package models.data.enumeration;

import com.avaje.ebean.annotation.EnumValue;

public enum RoleEnum {

	@EnumValue("A")
	ADMINISTRATOR,
	
	@EnumValue("M")
	MANAGEMENT, 
	 
	@EnumValue("P")
	ADVERTISER
	
}
