package models.form.backendForm.financeForm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.data.validation.ValidationError;
import play.i18n.Messages;
import models.custom_helper.DateBinder;
import models.data.enumeration.PaymentMethodEnum;

public class TransferForm {

	public String amount;
	public String description;
	public String transfer_date;
	public String senderBankAccount;
	SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy hh:mm");
	
	DateBinder binder=new DateBinder(); 
	public List<ValidationError> validate(){
		List<ValidationError> error= new ArrayList<ValidationError>();		
		try {
			int amounts=Integer.parseInt(amount);
			if(amounts<0){
				error.add(new ValidationError("amount",Messages.get("validation.amount_kurang")));
			}

			if(description.length()>500){
				error.add(new ValidationError("description",Messages.get("error.description",500+"")));
			}	
			Date date =format.parse(transfer_date);
			if(binder.isAfterToday(date)){
				error.add(new ValidationError("transfer_date", Messages.get("validation.transfer_date")));
			}
			if(senderBankAccount.length()==0){
				error.add(new ValidationError("senderBankAccont",Messages.get("error.description",500+"")));				
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			error.add(new ValidationError("description",Messages.get("constraint.wrong_number")));			
		} catch (ParseException e) {
			error.add(new ValidationError("transfer_date", Messages.get("validation.dateFormat")));
			e.printStackTrace();
		}
		
		return (error.size()==0)? null:error;
	}
}
