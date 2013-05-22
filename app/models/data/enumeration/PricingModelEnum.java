package models.data.enumeration;

import com.avaje.ebean.annotation.EnumValue;

public enum PricingModelEnum {
	@EnumValue("cpm")
	CPM,
	@EnumValue("cpa")
	CPA,
	@EnumValue("flat")
	FLAT,
}
