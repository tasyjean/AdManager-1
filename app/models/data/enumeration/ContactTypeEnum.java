package models.data.enumeration;

import com.avaje.ebean.annotation.EnumValue;

public enum ContactTypeEnum {
	
	@EnumValue("address")
	ADDRESS,
	@EnumValue("email")
	EMAIL,
	@EnumValue("home_phone")
	HOME_PHONE,
	@EnumValue("private_phone")
	PRIVATE_PHONE,
	@EnumValue("alternative_phone")
	ALTERNATIVE_PHONE,
	@EnumValue("social_profile")	
	SOCIAL_PROFILE,
	@EnumValue("other")
	OTHER
}
