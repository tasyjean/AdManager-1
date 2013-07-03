package models.dataWrapper.finance;

import java.util.ArrayList;
import java.util.List;

import models.data.User;
import models.data.UserRole;
import models.data.enumeration.PaymentMethodEnum;
import models.data.enumeration.RoleEnum;

public class DepositoData {

	private List<User> user;
	private List<String[]>   paymentMethod=new ArrayList<String[]>();
	
	public DepositoData() {
		UserRole advertiser_role=UserRole.find.where().eq("name", RoleEnum.ADVERTISER).findUnique();
		user=User.find.where().eq("role", advertiser_role).findList();
		
		for(PaymentMethodEnum enums:PaymentMethodEnum.values()){
			String[] input={enums.name(),enums.toString()};
			paymentMethod.add(input);
		}
	}

	public List<User> getUser() {
		return user;
	}

	public List<String[]> getPaymentMethod() {
		return paymentMethod;
	}

	
}
