package models.data.enumeration;

import com.avaje.ebean.annotation.EnumValue;

public enum ZoneTypeEnum {

	@EnumValue("text")
	TEXT,
	@EnumValue("object")
	OBJECT,  //flash, dll
	@EnumValue("image")
	IMAGE
}
