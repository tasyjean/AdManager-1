package models.data.enumeration;

import play.i18n.Messages;

import com.avaje.ebean.annotation.EnumValue;

public enum PaymentMethodEnum {

	@EnumValue("TRANSFER")
	TRANSFER(){
		public String toString() {
			return Messages.get("payment.transfer");
		}
	}
}
