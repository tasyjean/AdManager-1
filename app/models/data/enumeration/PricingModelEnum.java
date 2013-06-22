package models.data.enumeration;

import play.i18n.Messages;

import com.avaje.ebean.annotation.EnumValue;

public enum PricingModelEnum {
	@EnumValue("cpm")
	CPM{
		public String toString() {
			return Messages.get("pricingModel.cpm.name");
		}
	},
	@EnumValue("cpa")
	CPA{
		public String toString() {
			return Messages.get("pricingModel.cpa.name");
		}
	},
	@EnumValue("flat")
	FLAT{
		public String toString() {
			return Messages.get("pricingModel.flat.name");
		}
	};
	
	public String getDescription(PricingModelEnum enums){
		switch (enums) {
		case CPM	:return Messages.get("pricingModel.cpm.info");
		case CPA	:return Messages.get("pricingModel.cpa.info");
		case FLAT	:return Messages.get("pricingModel.flat.info");
		default:
			return "";
		}
	}
}
