package models.data.enumeration;

import com.avaje.ebean.annotation.EnumValue;

public enum ActionTypeEnum {

	@EnumValue("click")
	CLICK,
	@EnumValue("hide")
	HIDE,
	@EnumValue("like")
	LIKE
}
