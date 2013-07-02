package models.form.backendForm.financeForm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.data.validation.ValidationError;
import play.i18n.Messages;

import models.data.enumeration.PaymentMethodEnum;

public class DepositoForm {

	public String amount;
	public String paymentMethod;
	public String idUser;
	public String description;
	public List<ValidationError> validate(){
		List<ValidationError> error= new ArrayList<ValidationError>();		
		try {
			int amounts=Integer.parseInt(amount);
			if(amounts<0){
				error.add(new ValidationError("amount",Messages.get("validation.amount_kurang")));
			}
			if(description.length()==0){
				error.add(new ValidationError("description",Messages.get("constraint.required")));

			}
			if(description.length()>500){
				error.add(new ValidationError("description",Messages.get("error.description",500+"")));
			}	

		} catch (NumberFormatException e) {
			e.printStackTrace();
			error.add(new ValidationError("description",Messages.get("constraint.wrong_number")));			
		}
		
		return (error.size()==0)? null:error;		
	}
}
