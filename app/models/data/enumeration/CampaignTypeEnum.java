package models.data.enumeration;

import play.i18n.Messages;

import com.avaje.ebean.annotation.EnumValue;

public enum CampaignTypeEnum {
	
	@EnumValue("contract")
	CONTRACT{

		public String toString() {
			return Messages.get("campaign.contract.name");
		}
	},
	@EnumValue("exclusive")
	EXCLUSIVE{

		public String toString() {
			return Messages.get("campaign.exclusive.name");
		}
	};
	
	public String getDescription(CampaignTypeEnum enums){
		switch (enums) {
		case CONTRACT	:return Messages.get("campaign.contract.info");
		case EXCLUSIVE	:return Messages.get("campaign.exclusive.info");
		default:
			return "";
		}
	}
}
